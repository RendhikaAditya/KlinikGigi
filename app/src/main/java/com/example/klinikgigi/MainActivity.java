package com.example.klinikgigi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.klinikgigi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String dokter = null;
    String tipe = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDokter();
        getTipe();
    }

    private void getTipe() {
        binding.bgDiChat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgDiChat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtDiChat.setTextColor(R.color.white);

                binding.bgDitempat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtDitempat.setTextColor(R.color.black);

                dokter = "1";
            }
        });

        binding.bgDitempat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgDitempat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtDitempat.setTextColor(R.color.white);

                binding.bgDiChat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtDiChat.setTextColor(R.color.black);

                dokter = "1";
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
                binding.txtRiska.setTextColor(R.color.white);

                binding.bgRosa.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtRosa.setTextColor(R.color.black);

                dokter = "1";
            }
        });

        binding.bgRosa.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                binding.bgRosa.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondaryColor));
                binding.txtRosa.setTextColor(R.color.white);

                binding.bgRiska.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                binding.txtRiska.setTextColor(R.color.black);

                dokter = "2";
            }
        });
    }
}