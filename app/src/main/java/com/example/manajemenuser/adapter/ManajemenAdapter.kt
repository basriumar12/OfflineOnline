package com.example.manajemenuser.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.manajemenuser.R
import com.example.manajemenuser.pojo.ResponseGetAllOffline

class ManajemenAdapter(
    private val data: List<ResponseGetAllOffline>,
    private val activity: Activity,
    private val context: Context
) : RecyclerView.Adapter<ManajemenAdapter.ViewHolder>() {
    var mInflater: LayoutInflater
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context).inflate(
            R.layout.names, parent, false
        )
        return ViewHolder(itemLayoutView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.tvEmail.text = data[position].email
        holder.tvName.text = data[position].name
        if (data[position].status == 0) {
            holder.imageViewStatus.visibility = View.VISIBLE
            holder.cv.setBackgroundColor(Color.GRAY)
        } else {
            holder.imageViewStatus.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvEmail: TextView
        var imageViewStatus: TextView
        var cv: CardView

        init {
            tvName = itemView.findViewById(R.id.textViewName)
            cv = itemView.findViewById(R.id.cv)
            tvEmail = itemView.findViewById(R.id.textViewEmail)
            imageViewStatus = itemView.findViewById(R.id.textViewStatus)
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }
}