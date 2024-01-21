package com.example.laptrinhapp.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptrinhapp.MainActivity2;
import com.example.laptrinhapp.R;
import com.example.laptrinhapp.ScanAct;
import com.example.laptrinhapp.StdList;
import com.example.laptrinhapp.model.Course;

import java.util.List;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    Context context;
    List<Course> courses;



    public CourseViewAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(context).inflate(R.layout.courseview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.name.setText("Ten mon hoc: "+courses.get(position).getName());
        holder.id.setText(courses.get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StdList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("course_name",courses.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

}
