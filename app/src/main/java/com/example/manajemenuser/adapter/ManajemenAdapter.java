package com.example.manajemenuser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemenuser.R;
import com.example.manajemenuser.pojo.ResponseGetAllOffline;

import java.util.List;


public class ManajemenAdapter extends RecyclerView.Adapter<ManajemenAdapter.ViewHolder> {
    private List<ResponseGetAllOffline> data;
    private Activity activity;
    private Context context;
    LayoutInflater mInflater;


    public ManajemenAdapter(List<ResponseGetAllOffline> data, Activity act, Context ctx) {
        this.data = data;
        this.activity = act;
        this.context = ctx;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ManajemenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.names, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManajemenAdapter.ViewHolder holder, final int position) {
        holder.tvEmail.setText(data.get(position).getEmail());
        holder.tvName.setText(data.get(position).getName());
        if (data.get(position).getStatus() == 0) {
            holder.imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        } else {
            holder.imageViewStatus.setBackgroundResource(R.drawable.success);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail;
        ImageView imageViewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textViewName);
            tvEmail = itemView.findViewById(R.id.textViewEmail);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);


        }
    }
}
