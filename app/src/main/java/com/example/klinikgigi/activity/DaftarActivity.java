package com.example.klinikgigi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.databinding.ActivityDaftarBinding;
import com.example.klinikgigi.databinding.ActivityDokterBinding;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class DaftarActivity extends AppCompatActivity {
    private ActivityDaftarBinding binding;
    PrefManager prefManager;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Proses....").setCancelable(false).build();

        binding.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.edNamaPasien.getText().toString())) {
                    binding.edNamaPasien.setError("Nama Harus Di isi");
                }else if(TextUtils.isEmpty(binding.edAlamatPasien.getText().toString())) {
                    binding.edAlamatPasien.setError("Alamat Harus Di isi");
                }else if(TextUtils.isEmpty(binding.edNoTelpon.getText().toString())) {
                    binding.edNoTelpon.setError("No Telpon Harus Di isi");
                }else if(TextUtils.isEmpty(binding.edEmailPasien.getText().toString())) {
                    binding.edEmailPasien.setError("Email Harus Di isi");
                }else if(TextUtils.isEmpty(binding.edUsernamePasien.getText().toString())) {
                    binding.edUsernamePasien.setError("Your message");
                }else if(TextUtils.isEmpty(binding.edPasswordPasien.getText().toString())) {
                    binding.edPasswordPasien.setError("Your message");
                }else {
                    setDaftar();
                }
            }
        });

    }

    private void setDaftar() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"register.php")
                .addBodyParameter("nama", binding.edNamaPasien.getText().toString())
                .addBodyParameter("alamat", binding.edAlamatPasien.getText().toString())
                .addBodyParameter("nohp", binding.edNoTelpon.getText().toString())
                .addBodyParameter("email", binding.edEmailPasien.getText().toString())
                .addBodyParameter("username", binding.edUsernamePasien.getText().toString())
                .addBodyParameter("password", binding.edPasswordPasien.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            Log.d("sukses", "code : "+response);
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "code : "+anError);
                        Toast.makeText(getApplicationContext(), anError+"", Toast.LENGTH_LONG).show();
                    }
                });
    }
}