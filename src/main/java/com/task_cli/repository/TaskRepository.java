package com.task_cli.repository;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.TaskListResponse;

public interface TaskRepository {
    RepositoryResponse addTask(String task, String status);
    TaskListResponse taskList(String statusFilter);
}
