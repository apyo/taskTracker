package dto;

import com.example.spring_boot.entity.Task;
import lombok.Data;

import java.time.Instant;

@Data
public class TaskResponse {
    private String title;
    private String description;
    private Boolean completed;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskResponse(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
