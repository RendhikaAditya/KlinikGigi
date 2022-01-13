package com.example.klinikgigi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.adapter.AdapterChat;
import com.example.klinikgigi.adapter.AdapterRoom;
import com.example.klinikgigi.databinding.ActivityChatRoomBinding;
import com.example.klinikgigi.model.ModelChat;
import com.example.klinikgigi.model.ModelRoom;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatRoomActivity extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    PrefManager prefManager;
    List<ModelChat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);


        Intent intent = new Intent(getIntent());
        if (prefManager.getLvl().equalsIgnoreCase("1")) {
            binding.namaUser.setText(intent.getStringExtra("DOKTER"));
            binding.tipeUser.setText("Dokter");
        } else {
            binding.namaUser.setText(intent.getStringExtra("PASIEN"));
            binding.tipeUser.setText("Pasien");
        }
        binding.rvChat.setHasFixedSize(true);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        chats = new ArrayList<>();

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getDataChat(intent.getStringExtra("ID"));
                Log.d("load","load");
            }
        }, 0, 5000);
        getDataChat(intent.getStringExtra("ID"));
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                t.cancel();

            }
        });


        binding.imgSenderMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.txtChat.getText().toString().trim().length() != 0) {
                    kirimPesan(intent.getStringExtra("ID"));
                } else {
                    binding.txtChat.setError("Isi Pesan");
                }
            }
        });
    }

    private void kirimPesan(String id) {
        loading(true);
        AndroidNetworking.post(ApiServer.site_url + "kirim_pesan.php")
                .addBodyParameter("id", id)
                .addBodyParameter("type", prefManager.getLvl())
                .addBodyParameter("isi", binding.txtChat.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loading(false);
                            Log.d("sukses", "code : " + response);
                            if (response.getString("code").equalsIgnoreCase("1")) {
                                getDataChat(id);
                                binding.txtChat.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading(false);
                        Log.d("eror", "code : " + anError);
                        Toast.makeText(getApplicationContext(), anError + "", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getDataChat(String id) {
        AndroidNetworking.get(ApiServer.site_url + "get_chat.php?id=" + id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sukses", "chatList pasien :" + response);
                        try {
                            if (response.getString("kode").equalsIgnoreCase("1")) {

                                JSONArray d = response.getJSONArray("data");
                                chats = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);

                                    ModelChat rom = gson.fromJson(data + "", ModelChat.class);
                                    chats.add(rom);
                                }
                                AdapterChat adapter = new AdapterChat(chats);
                                binding.rvChat.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "chatList pasien : " + anError);
                    }
                });
    }

    public void loading(boolean loading) {
        if (loading) {
            binding.progres.setVisibility(View.VISIBLE);
            binding.imgSenderMessage.setVisibility(View.GONE);
        } else {
            binding.progres.setVisibility(View.GONE);
            binding.imgSenderMessage.setVisibility(View.VISIBLE);
        }
    }
}