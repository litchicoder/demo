package com.example.mydemoapplication.pinchzoomlayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemoapplication.R

class GridAdapter : RecyclerView.Adapter<GridItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.grid_item, parent, false)
        return GridItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.textView.text = position.toString()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}
