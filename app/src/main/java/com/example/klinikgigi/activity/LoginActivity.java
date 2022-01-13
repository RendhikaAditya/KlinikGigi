package com.example.klinikgigi.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.R;
import com.example.klinikgigi.databinding.ActivityLoginBinding;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    PrefManager prefManager;
    AlertDialog alertDialog;
    String tipeLogin="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Mencoba Masuk....").setCancelable(false).build();

        if (prefManager.getLoginStatus()){
            if (prefManager.getLvl().equalsIgnoreCase("1")){
                Intent intent = new Intent(LoginActivity.this, PasienActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(LoginActivity.this, DokterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }

        getTipeLogin();
        
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipeLogin.equalsIgnoreCase("1")) {
                    getLoginPasien();
                }else {
                    getLoginDokter();
                }
            }
        });
        
        
    }



    private void getTipeLogin() {
        binding.bgDokter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgDokter.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtDokter.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgPasien.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtPasien.setTextColor(Color.parseColor("#000000"));

                tipeLogin = "2";
            }
        });

        binding.bgPasien.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgPasien.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtPasien.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgDokter.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtDokter.setTextColor(Color.parseColor("#000000"));

                tipeLogin = "1";
            }
        });
    }

    private void getLoginDokter() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"login_dokter.php")
                .addBodyParameter("username", binding.edUsername.getText().toString())
                .addBodyParameter("password", binding.edPassword.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("hasil dokter", "code"+response);
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONObject data = response.getJSONObject("data");


                                prefManager.setIdUser(data.getString("id_dokter"));
                                prefManager.setLoginStatus(true);
                                prefManager.setNamaUser(data.getString("nama_dokter"));
                                prefManager.setNohpUser(data.getString("telp_dokter"));
                                prefManager.setLvl(tipeLogin);
                                Intent intent = new Intent(LoginActivity.this, DokterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(LoginActivity.this, "User Tidak Tersedia", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "login dokter : "+anError);
                    }
                });
    }

    private void getLoginPasien() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"login_pasien.php")
                .addBodyParameter("username", binding.edUsername.getText().toString())
                .addBodyParameter("password", binding.edPassword.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("hasil pasient", "code"+response);
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONObject data = response.getJSONObject("data");


                                    prefManager.setIdUser(data.getString("id_bumil"));
                                    prefManager.setLoginStatus(true);
                                    prefManager.setNamaUser(data.getString("nama_bumil"));
                                    prefManager.setNohpUser(data.getString("telp_bumil"));
                                    prefManager.setLvl(tipeLogin);
                                    Intent intent = new Intent(LoginActivity.this, PasienActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                            }else {
                                Toast.makeText(LoginActivity.this, "User Tidak Tersedia", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "login pasien : "+anError);
                    }
                });
    }

}