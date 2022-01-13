package com.example.klinikgigi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.R;
import com.example.klinikgigi.adapter.AdapterRiwayat;
import com.example.klinikgigi.databinding.ActivityDokterBinding;
import com.example.klinikgigi.model.ModelRiwayat;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class DokterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityDokterBinding binding;
    List<ModelRiwayat> riwayatsKonfir;
    List<ModelRiwayat> riwayatsBaru;
    PrefManager prefManager;
    AlertDialog alertDialog;

    TextView logout, nama, nohp;
    LinearLayout btnHistori;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDokterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Proses....").setCancelable(false).build();

        nama = findViewById(R.id.namaUserSide);
        nohp = findViewById(R.id.nohpUser);
        logout = findViewById(R.id.btnLogout);
        btnHistori = findViewById(R.id.btnHistori);

        btnHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DokterActivity.this, RiwayatActivity.class);
                startActivity(intent);
            }
        });

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DokterActivity.this, ChatListActivity.class);
                startActivity(intent);
            }
        });

        nohp.setText(prefManager.getNohpUser());
        nama.setText(prefManager.getNamaUser());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setLoginStatus(false);
                Intent intent = new Intent(DokterActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        binding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        riwayatsKonfir = new ArrayList<>();
        riwayatsBaru = new ArrayList<>();
        binding.rvJadwalBaru.setHasFixedSize(true);
        binding.rvSetujuiJadwal.setHasFixedSize(true);



        getKonsultasi();
    }



    private void getKonsultasi() {
        alertDialog.show();
        AndroidNetworking.get(ApiServer.site_url+"get_janji_id_dokter_terima.php?id="+prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sukses", "riwayat pasien :"+response);
                        try {
                            if (response.getString("kode").equalsIgnoreCase("1")){

                                JSONArray d = response.getJSONArray("data");
                                riwayatsBaru = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelRiwayat riwayat = gson.fromJson(data + "", ModelRiwayat.class);
                                    riwayatsBaru.add(riwayat);
                                }
                                AdapterRiwayat adapterRiwayat = new AdapterRiwayat(riwayatsBaru);
                                binding.rvJadwalBaru.setAdapter(adapterRiwayat);
                                adapterRiwayat.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror","riwayat pasien : "+anError);
                    }
                });

        AndroidNetworking.get(ApiServer.site_url+"get_janji_id_dokter_konfirmasi.php?id="+prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sukses", "riwayat pasien :"+response);
                        try {
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                riwayatsKonfir = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelRiwayat riwayat = gson.fromJson(data + "", ModelRiwayat.class);
                                    riwayatsKonfir.add(riwayat);
                                }
                                AdapterRiwayat adapterRiwayat = new AdapterRiwayat(riwayatsKonfir);
                                binding.rvSetujuiJadwal.setAdapter(adapterRiwayat);
                                adapterRiwayat.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror","riwayat pasien : "+anError);
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}