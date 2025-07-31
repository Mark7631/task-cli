package com.task_cli.handler;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.main.TaskService;
import com.task_cli.records.Task;
import com.task_cli.records.TaskListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TaskCommandHandler {
    private final TaskService taskService;

    public TaskCommandHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    public String addTask(String[] task, String status) {
        StringBuilder fullTask = new StringBuilder();
        for (String word : task) fullTask.append(word).append(" ");
        RepositoryResponse response;
        switch (status.toLowerCase()) {
            case "notdone": response = taskService.addTask(fullTask.toString().trim(), "notDone");
                            break;
            case "inprocess": response = taskService.addTask(fullTask.toString().trim(), "inProcess");
                            break;
            case "done": response = taskService.addTask(fullTask.toString().trim(), "done");
                            break;
            default: return "unexpected status, expected: notDone, inProcess, done";
        }

        if (response.equals(RepositoryResponse.OK)) {
            return "successful add";
        } else if (response.equals(RepositoryResponse.UNABLE_TASK)) {
            return "task already exist";
        } else if (response.equals(RepositoryResponse.FILE_ERROR)) {
            return "file error";
        } else {
            return "unexpected error";
        }
    }

    public String taskList(String statusFilter) {
        if (!statusFilter.equalsIgnoreCase("notDone") && !statusFilter.equalsIgnoreCase("inProcess") && !statusFilter.equalsIgnoreCase("done") && !statusFilter.equalsIgnoreCase("none")) return "incorrect status expected notDone/inProcess/done";
        TaskListResponse taskListResponse = taskService.taskList(statusFilter);
        switch (taskListResponse.repositoryResponse()) {
            case FILE_NOT_EXIST: return "error repository file not exist";
            case FILE_IS_EMPTY: return "no tasks";
            case FILE_ERROR: return "unknown file error";
            case OK:
                List<Task> tasks = taskListResponse.taskList();
                if (tasks.isEmpty()) return "no tasks";
                StringBuilder out = new StringBuilder().append("id                                   | task | status\n");
                tasks.forEach(task -> out.append(task.toString()).append("\n"));
                return out.toString();
            default:
                log.error("not handle response {}", taskListResponse.repositoryResponse());
                return "unknown error";
        }
    }
}
