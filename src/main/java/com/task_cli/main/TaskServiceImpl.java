package com.task_cli.main;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.TaskListResponse;
import com.task_cli.repository.TaskRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public RepositoryResponse addTask(String task, String status) {
        return taskRepository.addTask(task, status);
    }

    @Override
    public TaskListResponse taskList(String statusFilter) { return taskRepository.taskList(statusFilter); }
}
