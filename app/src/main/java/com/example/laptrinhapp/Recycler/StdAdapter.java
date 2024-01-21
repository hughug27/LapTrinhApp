package com.example.laptrinhapp.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptrinhapp.R;
import com.example.laptrinhapp.StdList;
import com.example.laptrinhapp.model.Student;

import java.util.List;

public class StdAdapter extends RecyclerView.Adapter<StdHolder> {
    Context context;
    List<Student> students;

    public StdAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StdHolder(LayoutInflater.from(context).inflate(R.layout.stdviewperclass,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StdHolder holder, int position) {
        holder.name.setText(students.get(position).getName());
        holder.id.setText(students.get(position).getStudentId());
        holder.num.setText("2");

    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
