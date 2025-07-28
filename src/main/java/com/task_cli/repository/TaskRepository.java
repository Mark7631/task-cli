package com.task_cli.repository;

import com.task_cli.enums.RepositoryResponse;

public interface TaskRepository {
    RepositoryResponse addTask(String task);
    RepositoryResponse addTask(String task, String status);
}
