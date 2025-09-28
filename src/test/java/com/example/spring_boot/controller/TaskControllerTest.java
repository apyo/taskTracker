package com.example.spring_boot.controller;

import com.example.spring_boot.repo.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TaskResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
}
