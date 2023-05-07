package com.example.todoapp;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {
    ArrayList<Task> arrayListTask = new ArrayList<>();

    public TaskRecyclerViewAdapter(ArrayList<Task> arrayListTask){
        this.arrayListTask = new ArrayList<>(arrayListTask);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.TaskViewHolder holder, int position) {
        // Ambil data dari setiap index di arraylist
        final Task task = arrayListTask.get(position);

        holder.taskTitle.setText(task.getTitle());
        holder.taskDeadline.setText(task.getDeadline());
        if(task.getUrgent()){
            holder.container.setCardBackgroundColor(ContextCompat.getColor(holder.container.getContext(), R.color.urgent));
        }
        else{
            holder.container.setCardBackgroundColor(ContextCompat.getColor(holder.container.getContext(), R.color.white));
        }

        // Pas di klik manggil activity baru yang nampilin detailnya
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manggil intent dan passing data item
                Intent intent = new Intent(holder.itemView.getContext(), DetailTaskActivity.class);
                intent.putExtra("TASK", task);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListTask.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi layout task
        TextView taskTitle, taskDeadline, taskDescription;
        MaterialCardView container;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDeadline = itemView.findViewById(R.id.taskDeadline);
            container = itemView.findViewById(R.id.cardView);
        }
    }
}
