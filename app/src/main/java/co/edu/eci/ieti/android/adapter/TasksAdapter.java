package co.edu.eci.ieti.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.network.model.Task;

public class TasksAdapter
        extends RecyclerView.Adapter<TasksAdapter.ViewHolder>
{

    List<Task> taskList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
    {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate(R.layout.task_row, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position )
    {
        Task task = taskList.get( position );
        holder.tPriority.setText(String.valueOf(task.getPriority()));
        holder.tId.setText(String.valueOf(task.getId()));
        holder.tName.setText(task.getDescription());
    }

    @Override
    public int getItemCount()
    {
        return taskList != null ? taskList.size() : 0;
    }

    public void updateTasks(List<Task> tasks){
        System.out.println("AQUI LLEGARON LAS TASKS");
        System.out.println(tasks);

        this.taskList = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder
            extends RecyclerView.ViewHolder
    {
        TextView tId;
        TextView tName;
        TextView tPriority;
        ViewHolder( @NonNull View itemView )
        {
            super( itemView );
            tId = itemView.findViewById(R.id.taskDueDate);
            tName = itemView.findViewById(R.id.taskDescription);
            tPriority = itemView.findViewById(R.id.taskPriority);

        }
    }

}
