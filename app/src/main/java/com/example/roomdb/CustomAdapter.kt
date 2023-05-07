package com.example.roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.model.NoteModel

class CustomAdapter(private val dataSource: List<NoteModel>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleTV)
        val desc = itemView.findViewById<TextView>(R.id.descTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_layout, parent, false)
        return CustomViewHolder(item)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.title.text = dataSource[position].title
        holder.desc.text = dataSource[position].description
    }

    override fun getItemCount(): Int {
       return dataSource.size
    }
}