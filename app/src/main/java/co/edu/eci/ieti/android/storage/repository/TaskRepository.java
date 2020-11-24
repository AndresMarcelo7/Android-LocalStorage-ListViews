package co.edu.eci.ieti.android.storage.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import co.edu.eci.ieti.android.network.model.Task;
import co.edu.eci.ieti.android.storage.TaskPlannerDatabase;
import co.edu.eci.ieti.android.storage.dao.TaskDao;

public class TaskRepository {
    private TaskDao taskDao;
    private List<Task> allTasks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TaskRepository(final Context context) {
        TaskPlannerDatabase db = TaskPlannerDatabase.getDatabase(context);
        taskDao = db.taskDao();
        allTasks = taskDao.getTasksByPriority();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<Task> getAllTasks() {
        return allTasks;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Task t) {
        TaskPlannerDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insert(t);
        });
    }
}
