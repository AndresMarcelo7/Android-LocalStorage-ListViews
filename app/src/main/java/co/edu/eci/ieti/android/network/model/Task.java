package co.edu.eci.ieti.android.network.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task
{
    @PrimaryKey
    @NonNull
    private long id;

    private String description;

    private int priority;



    public Task()
    {
    }

    public Task( long id, String description, int priority )
    {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority( int priority )
    {
        this.priority = priority;
    }


    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Task{" + "description='" + description + '\'' + ", priority=" + priority + ", dueDate="  + '}';
    }
}
