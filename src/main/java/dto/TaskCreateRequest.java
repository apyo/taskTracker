package dto;

import lombok.Data;

@Data
public class TaskCreateRequest {
    private String title;
    private String description;
}
