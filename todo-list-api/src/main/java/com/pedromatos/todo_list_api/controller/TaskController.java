package com.pedromatos.todo_list_api.controller;

import com.pedromatos.todo_list_api.dto.TaskRequestDTO;
import com.pedromatos.todo_list_api.dto.TaskResponseDTO;
import com.pedromatos.todo_list_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    @PostMapping
    public ResponseEntity<TaskResponseDTO> saveTask(@RequestBody TaskRequestDTO requestDTO){
        TaskResponseDTO response = taskService.saveTask(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
