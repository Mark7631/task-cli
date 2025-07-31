package com.task_cli.cliClient;

import com.task_cli.enums.RepositoryResponse;
import com.task_cli.handler.TaskCommandHandler;
import com.task_cli.main.TaskService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class СliClient {
    private final TaskCommandHandler taskCommandHandler;

    public СliClient(TaskCommandHandler taskCommandHandler) {
        this.taskCommandHandler = taskCommandHandler;
    }

    @ShellMethod(key = "add")
    public String addTask(@ShellOption String[] task, @ShellOption(defaultValue = "notDone") String status) {
        return taskCommandHandler.addTask(task, status);
    }

    @ShellMethod(key = "ls")
    public String taskList(@ShellOption(defaultValue = "none") String statusFilter) {
        return taskCommandHandler.taskList(statusFilter);
    }
}
