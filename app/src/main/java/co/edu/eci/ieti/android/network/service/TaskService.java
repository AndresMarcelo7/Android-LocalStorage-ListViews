package co.edu.eci.ieti.android.network.service;

import java.util.List;

import co.edu.eci.ieti.android.network.model.Task;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskService {
    @GET("api/task")
    Call<List<Task>> listTasks();
}
