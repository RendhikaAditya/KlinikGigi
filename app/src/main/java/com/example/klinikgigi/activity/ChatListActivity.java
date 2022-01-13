package com.example.klinikgigi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.adapter.AdapterRoom;
import com.example.klinikgigi.databinding.ActivityChatListBinding;
import com.example.klinikgigi.model.ModelRoom;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ChatListActivity extends AppCompatActivity {
    private ActivityChatListBinding binding;
    private PrefManager prefManager;
    AlertDialog alertDialog;
    List<ModelRoom> rooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Proses....").setCancelable(false).build();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupListener();
        if (prefManager.getLvl().equalsIgnoreCase("1")){
            getDataChat(prefManager.getIdUser(), "pasien");
        }else {
            getDataChat(prefManager.getIdUser(),"dokter");
        }


    }

    private void getDataChat(String idUser, String dokter) {
        alertDialog.show();
        AndroidNetworking.get(ApiServer.site_url+"get_room_chat.php?id="+idUser+"&user="+dokter)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sukses", "chatList pasien :"+response);
                        try {
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){

                                JSONArray d = response.getJSONArray("data");
                                rooms = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelRoom rom = gson.fromJson(data + "", ModelRoom.class);
                                    rooms.add(rom);
                                }
                                AdapterRoom adapter = new AdapterRoom(rooms);
                                binding.rvListChat.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror","chatList pasien : "+anError);
                    }
                });
    }

    private void setupListener() {


        binding.rvListChat.setHasFixedSize(true);
        binding.rvListChat.setLayoutManager(new LinearLayoutManager(this));
        rooms = new ArrayList<>();
    }
}