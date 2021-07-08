package com.example.klinikgigi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinikgigi.R;
import com.example.klinikgigi.databinding.RowJamBinding;
import com.example.klinikgigi.model.ModelJam;
import com.example.klinikgigi.util.OnUpdateJam;

import java.util.List;


public class AdapterJam extends RecyclerView.Adapter<AdapterJam.ListViewHolder> {

    Context context;
    List<ModelJam> jams;
    OnUpdateJam onUpdateJam;

    public AdapterJam(List<ModelJam> jams, OnUpdateJam onUpdateJam) {
        this.jams = jams;
        this.onUpdateJam = onUpdateJam;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(RowJamBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    int selectItem = -1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterJam.ListViewHolder holder, int position) {
        final ModelJam model = jams.get(position);
        holder.binding.txtJam.setText(model.getJam());
        holder.binding.bgJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItem=position;
                notifyDataSetChanged();
                onUpdateJam.onUpdateJam(model.getJam());
            }
        });
        if (selectItem==position){
            holder.binding.txtJam.setTextColor(Color.parseColor("#FFFFFF"));
            holder.binding.bgJam.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.secondaryColor));
        }else{
            holder.binding.txtJam.setTextColor(Color.parseColor("#000000"));
            holder.binding.bgJam.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return jams.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private RowJamBinding binding;
        public ListViewHolder(@NonNull RowJamBinding binding) {
            super(binding.getRoot());
            context = itemView.getContext();
            this.binding = binding;
        }
    }
}
