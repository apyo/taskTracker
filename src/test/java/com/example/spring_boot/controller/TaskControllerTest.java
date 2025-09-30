package com.example.spring_boot.controller;

import com.example.spring_boot.repo.TaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  //revert DB changes before each test
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Test
    public void createTask_shouldReturnTaskWithId() throws Exception {

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test item 1\"}"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test item 1"))
                .andExpect(jsonPath("$.completed").value(false));

    }

    @Test
    public void createTask_shouldReturn400WhenTitleIsEmpty() throws Exception {

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))

                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateTask_shouldUpdateValue() throws Exception {

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test item 1\"}"))
                .andExpect(status().isOk());

        Long taskId = repository.findAll().getFirst().getId();

        mockMvc.perform(patch("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test item 1 edited\", \"completed\":true}"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test item 1 edited"))
                .andExpect(jsonPath("$.completed").value(true));

    }

    @Test
    public void deleteTask_shouldReturnNoContent() throws Exception {

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test item 1\"}"))
                .andExpect(status().isOk());

        Long taskId = repository.findAll().getFirst().getId();

        mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isNoContent());

        assertTrue(repository.findById(taskId).isEmpty());

    }

    @Test
    public void deleteTask_shouldReturnNotFoundForWrongId() throws Exception {

        mockMvc.perform(delete("/api/tasks/9999"))
                .andExpect(status().isNotFound());

    }
}
