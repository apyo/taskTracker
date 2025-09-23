package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateRequest {
    @NotBlank
    @Size(max = 100)
    private String title;
    private String description;
    private Boolean completed;
}
