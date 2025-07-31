package com.task_cli.records;

import com.task_cli.enums.RepositoryResponse;

import java.util.List;

public record TaskListResponse(
        RepositoryResponse repositoryResponse,
        List<Task> taskList
) {
}
