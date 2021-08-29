package com.example.klinikgigi.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.klinikgigi.R;
import com.example.klinikgigi.adapter.AdapterJam;
import com.example.klinikgigi.databinding.ActivityPasienBinding;
import com.example.klinikgigi.model.ModelJam;
import com.example.klinikgigi.util.ApiServer;
import com.example.klinikgigi.util.OnUpdateJam;
import com.example.klinikgigi.util.PrefManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class PasienActivity extends AppCompatActivity implements OnUpdateJam, NavigationView.OnNavigationItemSelectedListener {
    private ActivityPasienBinding binding;
    String dokter = "1";
    String namaDokter = "drg. Riska Fitria";
    String tipe = "Melalui Chat";
    List<ModelJam> jams;
    AlertDialog alertDialog;
    PrefManager prefManager;
    String jam = null;

    final Calendar c = Calendar.getInstance();
    static final int DATE_DIALOG_ID = 999;
    private int year;
    private int month;
    private int day;


    private ActionBarDrawerToggle mToggle;

    TextView logout, nama, nohp;
    LinearLayout btnHistori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);
        prefManager = new PrefManager(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Proses....").setCancelable(false).build();

        nama = findViewById(R.id.namaUserSide);
        nohp = findViewById(R.id.nohpUser);
        logout = findViewById(R.id.btnLogout);
        btnHistori = findViewById(R.id.btnHistori);

        btnHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasienActivity.this, RiwayatActivity.class);
                startActivity(intent);
            }
        });

        nohp.setText(prefManager.getNohpUser());
        nama.setText(prefManager.getNamaUser());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setLoginStatus(false);
                Intent intent = new Intent(PasienActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM -dd");
        Date date = new Date();
        String now= formatter.format(date);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        binding.txtDate.setText(now);

        binding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // untuk toggle open dan close navigation
        mToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        // tambahkan mToggle ke drawer_layout sebagai pengendali open dan close drawer
        binding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        getDokter();
        getTipe();

        binding.rvJam.setHasFixedSize(true);
        binding.rvJam.setLayoutManager(new GridLayoutManager(this, 4));
        jams = new ArrayList<>();

        getJam(now, dokter);

        binding.btnBuatJanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanJanji();
            }
        });

        binding.btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }

    private void simpanJanji() {
        alertDialog.show();
        AndroidNetworking.post(ApiServer.site_url+"set_janji.php")
                .addBodyParameter("id", "1")
                .addBodyParameter("tgl", binding.txtDate.getText().toString())
                .addBodyParameter("jam", jam)
                .addBodyParameter("dok", dokter)
                .addBodyParameter("tipe", tipe)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            if (response.getString("code").equalsIgnoreCase("1")){
                                Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PasienActivity.this, BayarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("NAMA", namaDokter);
                                intent.putExtra("DATE", binding.txtDate.getText().toString());
                                intent.putExtra("TIME", jam);
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
                        Toast.makeText(getApplicationContext(), ""+anError, Toast.LENGTH_LONG).show();
                        alertDialog.hide();
                    }
                });
    }

    private void getJam(String date, String dokter) {
        alertDialog.show();
        Log.d("url ","jam : "+ApiServer.site_url+"get_jam.php?tgl="+date+"&id="+ dokter);
        AndroidNetworking.get(ApiServer.site_url+"get_jam.php?tgl="+date+"&id="+ dokter)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        alertDialog.hide();
                        try {
                            jams.clear();
                            Log.d("sukses","jam : "+response);
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                Log.d("data", "yeay : ");
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);
                                    jams.add(new ModelJam(data.getString("jam")));
                                }
                                getAdapterJam();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Log.d("eror", "jam : "+anError);
                    }
                });

    }

    private void getAdapterJam() {
        AdapterJam adapter = new AdapterJam(jams, this::onUpdateJam);
        binding.rvJam.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getTipe() {
        binding.bgDiChat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgDiChat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtDiChat.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgDitempat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtDitempat.setTextColor(Color.parseColor("#000000"));

                tipe = "Melalui Chat";
            }
        });

        binding.bgDitempat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgDitempat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtDitempat.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgDiChat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtDiChat.setTextColor(Color.parseColor("#000000"));

                tipe = "Di Tempat";
            }
        });
    }

    private void getDokter() {
        binding.bgRiska.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgRiska.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtRiska.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgRosa.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtRosa.setTextColor(Color.parseColor("#000000"));

                dokter = "1";
                namaDokter = "drg. Riska Fitria";
                getJam(binding.txtDate.getText().toString(), dokter);
            }
        });

        binding.bgRosa.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgRosa.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtRosa.setTextColor(Color.parseColor("#FFFFFF"));

                binding.bgRiska.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtRiska.setTextColor(Color.parseColor("#000000"));

                dokter = "2";
                namaDokter = "drg. Rosa";
                getJam(binding.txtDate.getText().toString(), dokter);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener datePickerListener =
            new    DatePickerDialog.   OnDateSetListener(){

                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;

                    StringBuilder date = new StringBuilder().append(year).append("-")
                            .append(month + 1).append("-").append(day).append(" ");
                    // set selected date into edittext
                    binding.txtDate.setText(new StringBuilder().append(year).append("-")
                            .append(month + 1).append("-").append(day).append(" "));
//                    String tgl = new StringBuilder().append(year).append("-")
//                            .append(month + 1).append("-").append(day).append(" ");
                    getJam(String.valueOf(date), dokter);
                }
            };


    @Override
    public void onUpdateJam(String j) {
        jam= j;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}