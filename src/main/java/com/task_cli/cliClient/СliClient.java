package com.task_cli.cliClient;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.main.TaskService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class СliClient {
    private final TaskService taskService;

    public СliClient(TaskService taskService) {
        this.taskService = taskService;
    }

    @ShellMethod(key = "add")
    public String addTask(@ShellOption String task, @ShellOption(defaultValue = "notDone") String status) {
        RepositoryResponse response;
        if (status.equals("notDone")) {
            response = taskService.addTask(task);
        } else if (status.equals("inProcess") || status.equals("done")) {
            response = taskService.addTask(task, status);
        } else {
            return "unexpected status, expected: notDone, inProcess, done";
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
}
