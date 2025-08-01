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
            List<Task> tasks;
            if (file.length() != 0) {
                tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});
                boolean isBusy = false;
                for (Task t : tasks) {
                    isBusy = t.task().equalsIgnoreCase(task);
                    if (isBusy) break;
                }
                if (isBusy) return RepositoryResponse.UNABLE_TASK;
            } else {
                tasks = new ArrayList<>();
            }
            tasks.add(new Task(task, status));
            objectMapper.writeValue(file, tasks);
            return RepositoryResponse.OK;
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }

    @Override
    public RepositoryResponse deleteTask(String task) {
        try {
            File file = new File(repoFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Task> tasks;
            if (file.length() != 0) {
                tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).task().equalsIgnoreCase(task)) {
                        tasks.remove(i);
                        objectMapper.writeValue(file, tasks);
                        return RepositoryResponse.OK;
                    }
                }
                return RepositoryResponse.NO_SUCH_TASK;
            } else {
                return RepositoryResponse.FILE_IS_EMPTY;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return RepositoryResponse.FILE_ERROR;
        }
    }

    @Override
    public RepositoryResponse updateTask(String oldTask, String newTask) {
        try {
            File file = new File(repoFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Task> tasks;
            if (file.length() != 0) {
                tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});

                for (Task task : tasks) {
                    if (task.task().equalsIgnoreCase(newTask)) return RepositoryResponse.UNABLE_TASK;
                }

                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).task().equalsIgnoreCase(oldTask)) {
                        String status = tasks.get(i).status();
                        tasks.remove(i);
                        tasks.add(new Task(newTask, status));
                        objectMapper.writeValue(file, tasks);
                        return RepositoryResponse.OK;
                    }
                }
                return RepositoryResponse.NO_SUCH_TASK;
            } else {
                return RepositoryResponse.FILE_IS_EMPTY;
            }
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
            List<Task> tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});
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
