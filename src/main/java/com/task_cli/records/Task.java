package com.task_cli.records;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Task(
        UUID id,
        String task,
        String status
) {
}
