package com.example.laptrinhapp.Recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptrinhapp.R;

import org.w3c.dom.Text;

public class StdHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView id;
    TextView num;
    public StdHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        id = itemView.findViewById(R.id.id);
        num = itemView.findViewById(R.id.textNum);
    }
}
