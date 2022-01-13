package com.example.klinikgigi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinikgigi.activity.ChatRoomActivity;
import com.example.klinikgigi.databinding.RowChatBinding;
import com.example.klinikgigi.databinding.RowChatListBinding;
import com.example.klinikgigi.model.ModelChat;
import com.example.klinikgigi.model.ModelRoom;
import com.example.klinikgigi.util.PrefManager;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ListViewHolder> {
    Context context;
    List<ModelChat> chats;

    public AdapterChat(List<ModelChat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new AdapterChat.ListViewHolder(RowChatBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterChat.ListViewHolder holder, int position) {
        final ModelChat model = chats.get(position);
        holder.binding.txtIsiChat.setText(model.getIsi_chat());
        holder.binding.txtIsiChatD.setText(model.getIsi_chat());

        if(model.getType().equalsIgnoreCase("0")){
            ///sisi orang
            holder.binding.layoutJikaPasien.setVisibility(View.GONE);
            holder.binding.layoutJikaDokter.setVisibility(View.VISIBLE);
        }else {
            ///sisi kita
            holder.binding.layoutJikaPasien.setVisibility(View.VISIBLE);
            holder.binding.layoutJikaDokter.setVisibility(View.GONE);
        }
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getLvl().equalsIgnoreCase("1")){
            if(!model.getType().equalsIgnoreCase("1")){
                ///sisi orang
                holder.binding.layoutJikaPasien.setVisibility(View.GONE);
                holder.binding.layoutJikaDokter.setVisibility(View.VISIBLE);
            }else {
                ///sisi kita
                holder.binding.layoutJikaPasien.setVisibility(View.VISIBLE);
                holder.binding.layoutJikaDokter.setVisibility(View.GONE);
            }
        }else {
            if(!model.getType().equalsIgnoreCase("1")){
                ///sisi kita
                holder.binding.layoutJikaPasien.setVisibility(View.VISIBLE);
                holder.binding.layoutJikaDokter.setVisibility(View.GONE);
            }else {
                ///sisi orang
                holder.binding.layoutJikaPasien.setVisibility(View.GONE);
                holder.binding.layoutJikaDokter.setVisibility(View.VISIBLE);
            }
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChatRoomActivity.class);
//                intent.putExtra("PASIEN", model.getNama_bumil());
//                intent.putExtra("DOKTER", model.getNama_dokter());
//                intent.putExtra("ID", model.getId_janji());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private RowChatBinding binding;
        public ListViewHolder(@NonNull RowChatBinding binding) {
            super(binding.getRoot());
            context = itemView.getContext();
            this.binding = binding;
        }
    }
}
