package com.task_cli.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public RepositoryResponse addTask(String task) {
        File file = new File("tasks.json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Task> map = objectMapper.readValue(file, HashMap.class);
            if (map.get(task) != null) return RepositoryResponse.UNABLE_TASK;
            objectMapper.writeValue(file, new Task(UUID.randomUUID(), task, "notDone"));
            return RepositoryResponse.OK;
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }

    @Override
    public RepositoryResponse addTask(String task, String status) {
        try {
            File file = new File("tasks.json");
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Task> map = objectMapper.readValue(file, HashMap.class);
            if (map.get(task) != null) return RepositoryResponse.UNABLE_TASK;
            objectMapper.writeValue(file, new Task(UUID.randomUUID(), task, status));
            return RepositoryResponse.OK;
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }
}
