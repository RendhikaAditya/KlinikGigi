package com.example.klinikgigi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinikgigi.activity.ChatRoomActivity;
import com.example.klinikgigi.activity.KonfirmasiActivity;
import com.example.klinikgigi.databinding.RowChatListBinding;
import com.example.klinikgigi.databinding.RowRiwayatBinding;
import com.example.klinikgigi.model.ModelRiwayat;
import com.example.klinikgigi.model.ModelRoom;
import com.example.klinikgigi.util.PrefManager;

import java.util.List;

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.ListViewHolder> {
    Context context;
    List<ModelRoom> rooms;

    public AdapterRoom(List<ModelRoom> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new AdapterRoom.ListViewHolder(RowChatListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterRoom.ListViewHolder holder, int position) {
        final ModelRoom model = rooms.get(position);
        holder.binding.jadwal.setText(model.getTgl_janji()+"  "+model.getJam());
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getLvl().equalsIgnoreCase("1")){
            holder.binding.namaUser.setText(model.getNama_dokter());
        }else {
            holder.binding.namaUser.setText(model.getNama_bumil());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("PASIEN", model.getNama_bumil());
                intent.putExtra("DOKTER", model.getNama_dokter());
                intent.putExtra("ID", model.getId_room());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private RowChatListBinding binding;
        public ListViewHolder(@NonNull RowChatListBinding binding) {
            super(binding.getRoot());
            context = itemView.getContext();
            this.binding = binding;
        }
    }
}
