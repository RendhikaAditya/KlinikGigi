package com.example.klinikgigi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinikgigi.activity.KonfirmasiActivity;
import com.example.klinikgigi.databinding.RowJamBinding;
import com.example.klinikgigi.databinding.RowRiwayatBinding;
import com.example.klinikgigi.model.ModelRiwayat;
import com.example.klinikgigi.util.PrefManager;

import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ListViewHolder> {
    Context context;
    List<ModelRiwayat> riwayats;

    public AdapterRiwayat(List<ModelRiwayat> riwayats) {
        this.riwayats = riwayats;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new AdapterRiwayat.ListViewHolder(RowRiwayatBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterRiwayat.ListViewHolder holder, int position) {
        final ModelRiwayat modelRiwayat = riwayats.get(position);
        holder.binding.txtDate.setText(modelRiwayat.getTglJanji());
        holder.binding.txtTime.setText(modelRiwayat.getJam());
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getLvl().equalsIgnoreCase("1")){
            holder.binding.namaUser.setText(modelRiwayat.getNamaDokter());
            holder.binding.nohpUser.setText(modelRiwayat.getTelpDokter());
        }else {
            holder.binding.namaUser.setText(modelRiwayat.getNamaBumil());
            holder.binding.nohpUser.setText(modelRiwayat.getTelpBumil());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KonfirmasiActivity.class);
                intent.putExtra("NAMA", holder.binding.namaUser.getText().toString());
                intent.putExtra("NOHP", holder.binding.nohpUser.getText().toString());
                intent.putExtra("TIME", modelRiwayat.getJam());
                intent.putExtra("DATE", modelRiwayat.getTglJanji());
                intent.putExtra("FOTO", modelRiwayat.getFotoBayar());
                intent.putExtra("STATUS", modelRiwayat.getStatusBayar());
                intent.putExtra("IDJANJI", modelRiwayat.getIdJanji());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return riwayats.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private RowRiwayatBinding binding;
        public ListViewHolder(@NonNull RowRiwayatBinding binding) {
            super(binding.getRoot());
            context = itemView.getContext();
            this.binding = binding;
        }
    }
}
