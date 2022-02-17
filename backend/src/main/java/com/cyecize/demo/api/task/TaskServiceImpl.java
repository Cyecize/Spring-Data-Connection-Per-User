package com.cyecize.demo.api.task;

import com.cyecize.demo.api.user.User;
import com.cyecize.demo.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cyecize.demo.api.task.TaskSpecifications.descriptionContains;
import static com.cyecize.demo.api.task.TaskSpecifications.hasDueDate;
import static com.cyecize.demo.api.task.TaskSpecifications.isInProgress;
import static com.cyecize.demo.api.task.TaskSpecifications.sort;
import static com.cyecize.demo.api.task.TaskSpecifications.userIdEquals;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public Task createTask(TaskCreateDto dto, User currentUser) {
        final Task task = this.modelMapper.map(dto, Task.class);
        task.setCreateDate(LocalDateTime.now());
        task.setInProgress(true);
        task.setUserId(currentUser.getId());

        return this.taskRepository.save(task);
    }

    @Override
    public Page<Task> searchTasks(TaskQuery query, User currentUser) {
        final Specification<Task> specification = userIdEquals(currentUser.getId())
                .and(hasDueDate(query.getHasDueDate()))
                .and(isInProgress(query.getInProgress()))
                .and(descriptionContains(query.getDescription()))
                .and(sort(query.getSortQuery().getOrderBy(), query.getSortQuery().getDirection()));

        return this.taskRepository.findAll(specification, PageRequest.of(query.getPage(), query.getSize()));
    }

    @Override
    public Task editTask(TaskCreateDto dto, Long taskId, User currentUser) {
        final Task task = this.getTask(taskId, currentUser);
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        if (dto.getInProgress() != null) {
            task.setInProgress(dto.getInProgress());
        }

        return this.taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId, User currentUser) {
        final Task task = this.getTask(taskId, currentUser);
        this.taskRepository.delete(task);
    }

    private Task getTask(long taskId, User currentUser) {
        final Optional<Task> optionalTask = this.taskRepository.findById(taskId);
        if (optionalTask.isEmpty() || !optionalTask.get().getUserId().equals(currentUser.getId())) {
            throw new ApiException("This task does not belong to you!");
        }

        return optionalTask.get();
    }
}
