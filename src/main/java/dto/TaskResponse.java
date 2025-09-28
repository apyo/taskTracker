package dto;

import com.example.spring_boot.entity.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TaskResponse {
    private long id;
    private String title;
    private String description;
    private Boolean completed;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
