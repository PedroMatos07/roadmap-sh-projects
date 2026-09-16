package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.dto.TaskRequestDTO;
import com.pedromatos.todo_list_api.dto.TaskResponseDTO;
import com.pedromatos.todo_list_api.model.Task;
import com.pedromatos.todo_list_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO requestDTO){
        Task newTask = new Task(null, requestDTO.title(),requestDTO.description());

        Task taskSaved = taskRepository.save(newTask);

        return new TaskResponseDTO(taskSaved.getId(),taskSaved.getTitle(),taskSaved.getDescription());
    }


}
