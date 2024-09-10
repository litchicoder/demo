package com.example.mydemoapplication.pinchzoomlayout;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydemoapplication.R;

public class GridItemViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public GridItemViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView) itemView.findViewById(R.id.item_text);
    }
}
