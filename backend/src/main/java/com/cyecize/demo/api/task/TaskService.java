package com.cyecize.demo.api.task;

import com.cyecize.demo.api.user.User;
import org.springframework.data.domain.Page;

public interface TaskService {

    Task createTask(TaskCreateDto dto, User currentUser);

    Page<Task> searchTasks(TaskQuery query, User currentUser);

    Task editTask(TaskCreateDto dto, Long taskId, User currentUser);

    void deleteTask(Long taskId, User currentUser);
}
