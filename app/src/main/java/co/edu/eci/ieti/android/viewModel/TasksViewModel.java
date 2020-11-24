package co.edu.eci.ieti.android.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.adapter.TasksAdapter;
import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.network.model.Task;
import co.edu.eci.ieti.android.storage.repository.TaskRepository;
import retrofit2.Response;

import static co.edu.eci.ieti.android.storage.TaskPlannerDatabase.databaseWriteExecutor;


public class TasksViewModel extends ViewModel {

    private List<Task> tasks;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private RetrofitNetwork retrofitNetwork;
    private Object lock = new Object();
    public List<Task> getTasks(String token, Context context, TasksAdapter tasksAdapter) {
        retrofitNetwork = new RetrofitNetwork(token);
        if (tasks == null) {
            tasks = new ArrayList<Task>();
            loadTasks(context,tasksAdapter);
        }

       synchronized (lock){
           while(tasks.size()==0){
               try {
                   lock.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           System.out.println("RETORNANDOOOO");
           System.out.println(tasks);
           return tasks;
       }
    }

    private void loadTasks(Context context,TasksAdapter tasksAdapter){
        executorService.execute( new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    synchronized (lock){
                        Response<List<Task>> response = retrofitNetwork.getTaskService().listTasks().execute();
                        tasks = response.body();
                        //tasks.postValue(tasks1);
                        System.out.println("TASKSSSSSSSS live data");
                        //System.out.println(tasks.getValue());
                        System.out.println(tasks);
                        storeTasks(tasks, context);
                        lock.notify();

                    }

                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }

    private void storeTasks(List<Task> tasks,Context context) {
        TaskRepository taskr = new TaskRepository(context);

        //TaskPlannerDatabase db = TaskPlannerDatabase.getDatabase(this);
        //TaskDao taskDao = db.taskDao();

        databaseWriteExecutor.execute(()->{
            for(Task t: tasks){
                System.out.println(t);
                //taskDao.insert(t);
                taskr.insert(t);

            }


            //List<Task> allTasks = taskDao.getTasksByPriority();

            System.out.println("coño funca?????");
            System.out.println(taskr.getAllTasks());
            //System.out.println(allTasks);
        });
    }

}
