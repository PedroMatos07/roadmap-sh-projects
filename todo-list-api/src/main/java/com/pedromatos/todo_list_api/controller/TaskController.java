package com.pedromatos.todo_list_api.controller;

import com.pedromatos.todo_list_api.dto.PagedTaskResponseDTO;
import com.pedromatos.todo_list_api.dto.TaskRequestDTO;
import com.pedromatos.todo_list_api.dto.TaskResponseDTO;
import com.pedromatos.todo_list_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> saveTask(@RequestBody TaskRequestDTO requestDTO, Authentication authentication){
        String userEmail = authentication.getName();
        TaskResponseDTO response = taskService.saveTask(requestDTO,userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO requestDTO, Authentication authentication){
        String userEmail = authentication.getName();
        TaskResponseDTO responseDTO = taskService.updateTask(id,requestDTO,userEmail);
        return  ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication){
        String userEmail = authentication.getName();
        taskService.deleteTask(id,userEmail);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedTaskResponseDTO> getTask(@RequestParam (defaultValue = "1") int page, @RequestParam (defaultValue = "10") int limit, Authentication authentication){
        String userEmail = authentication.getName();
        PagedTaskResponseDTO responseDTO = taskService.getTasks(page, limit, userEmail);
        return ResponseEntity.ok(responseDTO);
    }

}
