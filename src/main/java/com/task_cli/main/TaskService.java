package com.task_cli.main;

import com.task_cli.enums.RepositoryResponse;

public interface TaskService {
    RepositoryResponse addTask(String task);
    RepositoryResponse addTask(String task, String status);
}
