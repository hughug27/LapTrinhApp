package com.example.laptrinhapp.Recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptrinhapp.R;

public class CourseViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView id;
    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        id = itemView.findViewById(R.id.id);
    }
}
