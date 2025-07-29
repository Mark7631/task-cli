package com.task_cli.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_cli.enums.RepositoryResponse;
import com.task_cli.records.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Value("${repository}")
    private String repoFilePath;

    @Override
    public RepositoryResponse addTask(String task) {
        try {
            File file = new File(repoFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Task> map;
            if (file.length() != 0) {
                map = objectMapper.readValue(file, HashMap.class);
                if (map.get(task) != null) return RepositoryResponse.UNABLE_TASK;
            } else {
                map = new HashMap<>();
            }
            map.put(task, new Task(UUID.randomUUID(), task, "notDone"));
            objectMapper.writeValue(file, map);
            return RepositoryResponse.OK;
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }

    @Override
    public RepositoryResponse addTask(String task, String status) {
        try {
            File file = new File(repoFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Task> map;
            if (file.length() != 0) {
                map = objectMapper.readValue(file, HashMap.class);
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
}
