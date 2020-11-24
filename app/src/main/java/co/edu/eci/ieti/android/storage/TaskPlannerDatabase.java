package co.edu.eci.ieti.android.storage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.network.model.Task;
import co.edu.eci.ieti.android.network.model.User;
import co.edu.eci.ieti.android.storage.dao.TaskDao;
import co.edu.eci.ieti.android.storage.dao.UserDao;

@Database(entities = {Task.class, User.class}, version = 1, exportSchema = false)
public abstract class TaskPlannerDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract UserDao userDao();

    private static volatile TaskPlannerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TaskDao dao = INSTANCE.taskDao();
                UserDao udao = INSTANCE.userDao();
                dao.deleteAll();
                udao.deleteAll();
            });
        }
    };

    public static TaskPlannerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskPlannerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskPlannerDatabase.class, "taskplanner_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
