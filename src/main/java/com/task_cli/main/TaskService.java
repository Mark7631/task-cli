package com.task_cli.main;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.TaskListResponse;

public interface TaskService {
    RepositoryResponse addTask(String task, String status);
    TaskListResponse taskList(String statusFilter);
}
