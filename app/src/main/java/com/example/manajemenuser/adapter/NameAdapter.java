package com.example.manajemenuser.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.manajemenuser.R;
import com.example.manajemenuser.pojo.ResponseGetAll;
import com.example.manajemenuser.pojo.ResponseGetAllOffline;

import java.util.List;



public class NameAdapter extends ArrayAdapter<ResponseGetAllOffline> {
    private List<ResponseGetAllOffline> names;
    private Context context;


    public NameAdapter( Context context, int resource, List<ResponseGetAllOffline> names) {
        super(context, resource);
        this.names = names;
        this.context = context;
    }

    @Override
    public View getView (int position, View convertview, ViewGroup parent){
        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.names, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        ResponseGetAllOffline name = names.get(position);
        Log.e("TAG","nilai "+name.getEmail());

        //setting the name to textview
        textViewName.setText(name.getName());
        textViewEmail.setText(name.getEmail());

            if (name.getStatus()==0) {
                imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
            }
                else
                imageViewStatus.setBackgroundResource(R.drawable.success);

                return listViewItem;
            }
    }

