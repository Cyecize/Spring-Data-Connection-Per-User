package com.cyecize.demo.web;

import com.cyecize.demo.api.task.TaskCreateDto;
import com.cyecize.demo.api.task.TaskDto;
import com.cyecize.demo.api.task.TaskQuery;
import com.cyecize.demo.api.task.TaskService;
import com.cyecize.demo.api.user.User;
import com.cyecize.demo.configuration.security.CurrentUser;
import com.cyecize.demo.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isFullyAuthenticated()")
public class TaskController {

    private final ModelMapper modelMapper;

    private final TaskService taskService;

    @PostMapping(Endpoints.TASKS_SEARCH)
    public Page<TaskDto> searchTasks(@Valid @RequestBody TaskQuery query, @CurrentUser User currentUser) {
        return this.taskService.searchTasks(query, currentUser).map(task -> this.modelMapper.map(task, TaskDto.class));
    }

    @PostMapping(Endpoints.TASKS)
    public TaskDto createTask(@Valid @RequestBody TaskCreateDto dto, @CurrentUser User currentUser) {
        return this.modelMapper.map(this.taskService.createTask(dto, currentUser), TaskDto.class);
    }

    @PatchMapping(Endpoints.TASK)
    public TaskDto editTask(@PathVariable("id") long taskId,
                            @Valid @RequestBody TaskCreateDto dto,
                            @CurrentUser User currentUser) {
        return this.modelMapper.map(this.taskService.editTask(dto, taskId, currentUser), TaskDto.class);
    }

    @DeleteMapping(Endpoints.TASK)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable("id") long taskId, @CurrentUser User currentUser) {
        this.taskService.deleteTask(taskId, currentUser);
    }
}
