package com.task_cli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.Task;
import com.task_cli.records.TaskListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Value("${repository}")
    private String repoFilePath;

    @Override
    public RepositoryResponse addTask(String task, String status) {
        try {
            File file = new File(repoFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Task> map;
            if (file.length() != 0) {
                map = objectMapper.readValue(file, Map.class);
                if (map.get(task) != null) return RepositoryResponse.UNABLE_TASK;
            } else {
                map = new HashMap<>();
            }
            map.put(task, new Task(UUID.randomUUID(), task, status));
            objectMapper.writeValue(file, map);
            return RepositoryResponse.OK;
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }

    @Override
    public TaskListResponse taskList(String statusFilter) {
        File file = new File(repoFilePath);
        if (!file.exists()) return new TaskListResponse(RepositoryResponse.FILE_NOT_EXIST, new ArrayList<>());
        if (file.length() == 0) return new TaskListResponse(RepositoryResponse.FILE_IS_EMPTY, new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Task> map = objectMapper.readValue(file, new TypeReference<Map<String, Task>>() {});
            List<Task> tasks = new ArrayList<>();
            for (Task task : map.values()) {
                tasks.add(task);
            }
            TaskListResponse response;
            if (statusFilter.equals("none")) {
                response = new TaskListResponse(RepositoryResponse.OK, tasks);
            } else {
                response = new TaskListResponse(RepositoryResponse.OK, tasks.stream().filter(task -> task.status().equalsIgnoreCase(statusFilter)).toList());
            }
            return response;
        } catch (IOException e) {
            log.error("read file error " + e.getMessage());
            return new TaskListResponse(RepositoryResponse.FILE_ERROR, new ArrayList<>());
        }
    }
}
