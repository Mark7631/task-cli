package com.task_cli.records;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Task (
        UUID id,
        String task,
        String status
) {
    @Override
    public String toString() {
        return String.format("%s | %s | %s", id.toString(), task, status);
    }
}
