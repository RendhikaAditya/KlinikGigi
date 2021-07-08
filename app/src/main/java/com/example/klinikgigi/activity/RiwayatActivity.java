package com.example.klinikgigi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.adapter.AdapterRiwayat;
import com.example.klinikgigi.databinding.ActivityRiwayatBinding;
import com.example.klinikgigi.model.ModelRiwayat;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RiwayatActivity extends AppCompatActivity {
    private ActivityRiwayatBinding binding;
    PrefManager prefManager;
    AlertDialog alertDialog;
    List<ModelRiwayat> riwayats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiwayatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        AndroidNetworking.initialize(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Proses....").setCancelable(false).build();

        binding.rvRiwayat.setHasFixedSize(true);
        binding.rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        riwayats = new ArrayList<>();

        if (prefManager.getLvl().equalsIgnoreCase("1")) {
            getRiwayatPasien();
            binding.swRiwayat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getRiwayatPasien();
                }
            });
        }else {
            getRiwayatDokter();
            binding.swRiwayat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getRiwayatDokter();
                }
            });
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getRiwayatDokter() {
        alertDialog.show();
        AndroidNetworking.get(ApiServer.site_url+"get_janji_id_dokter.php?id="+prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sukses", "riwayat pasien :"+response);
                        alertDialog.hide();
                        try {
                            if (response.getString("kode").equalsIgnoreCase("1")){

                                JSONArray d = response.getJSONArray("data");
                                riwayats = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelRiwayat riwayat = gson.fromJson(data + "", ModelRiwayat.class);
                                    riwayats.add(riwayat);
                                }
                                AdapterRiwayat adapterRiwayat = new AdapterRiwayat(riwayats);
                                binding.rvRiwayat.setAdapter(adapterRiwayat);
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

    private void getRiwayatPasien(){
        alertDialog.show();
        AndroidNetworking.get(ApiServer.site_url+"get_janji_id_pasien.php?id="+"1")
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
                                riwayats = new ArrayList<>();
                                Gson gson = new Gson();
                                for (int i=0; i<d.length(); i++){
                                    JSONObject data = d.getJSONObject(i);

                                    ModelRiwayat riwayat = gson.fromJson(data + "", ModelRiwayat.class);
                                    riwayats.add(riwayat);
                                }
                                AdapterRiwayat adapterRiwayat = new AdapterRiwayat(riwayats);
                                binding.rvRiwayat.setAdapter(adapterRiwayat);
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
}