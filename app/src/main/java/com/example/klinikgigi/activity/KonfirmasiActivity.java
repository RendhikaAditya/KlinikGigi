package com.example.klinikgigi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.databinding.ActivityKonfirmasiBinding;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class KonfirmasiActivity extends AppCompatActivity {
    private ActivityKonfirmasiBinding binding;
    PrefManager prefManager;
    AlertDialog dialog;
    String idJanji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKonfirmasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);
        AndroidNetworking.initialize(this);
        dialog = new SpotsDialog.Builder().setContext(KonfirmasiActivity.this).setMessage("Sedang Proses ....").setCancelable(false).build();

        Intent intent = new Intent(getIntent());
        idJanji = intent.getStringExtra("IDJANJI");
        binding.namaUser.setText(intent.getStringExtra("NAMA"));
        binding.nohpUser.setText(intent.getStringExtra("NOHP"));
        binding.txtDate.setText(intent.getStringExtra("DATE"));
        binding.txtTime.setText(intent.getStringExtra("TIME"));
        Picasso.get().load(ApiServer.img+intent.getStringExtra("FOTO")).into(binding.imgPlace);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (prefManager.getLvl().equalsIgnoreCase("1")){
            binding.btn.setVisibility(View.GONE);
        }else {
            if (!intent.getStringExtra("STATUS").equalsIgnoreCase("MENUNGGU KONFIRMASI")){
                binding.btn.setVisibility(View.GONE);
            }else {
                binding.btn.setVisibility(View.VISIBLE);
            }
        }


        
        binding.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTerima();
            }
        });
        
        binding.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTolak();
            }
        });

        binding.imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog builder = new Dialog(KonfirmasiActivity.this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

                ImageView imageView = new ImageView(KonfirmasiActivity.this);
                Picasso.get().load(ApiServer.img+intent.getStringExtra("FOTO")).into(imageView);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();
            }
        });

    }

    private void setTolak() {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url + "set_tolak.php")
                .addBodyParameter("id", idJanji)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("code", "sikses" + response);
                            dialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")) {
                                Toast.makeText(KonfirmasiActivity.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KonfirmasiActivity.this, DokterActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(KonfirmasiActivity.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("code", "sikses" + anError);
                        Toast.makeText(KonfirmasiActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setTerima() {
        dialog.show();
        AndroidNetworking.post(ApiServer.site_url + "set_terima.php")
                .addBodyParameter("id", idJanji)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("code", "sikses" + response);
                            dialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")) {
                                Toast.makeText(KonfirmasiActivity.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KonfirmasiActivity.this, DokterActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(KonfirmasiActivity.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Log.d("code", "sikses" + anError);
                        Toast.makeText(KonfirmasiActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}