package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.dto.PagedTaskResponseDTO;
import com.pedromatos.todo_list_api.dto.TaskRequestDTO;
import com.pedromatos.todo_list_api.dto.TaskResponseDTO;
import com.pedromatos.todo_list_api.exception.ResourceNotFoundException;
import com.pedromatos.todo_list_api.model.Task;
import com.pedromatos.todo_list_api.repository.TaskRepository;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public TaskResponseDTO updateTask(Long id,TaskRequestDTO requestDTO){
        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setDescription(requestDTO.description());
        task.setTitle(requestDTO.title());
        Task taskSaved = taskRepository.save(task);
        return new TaskResponseDTO(taskSaved.getId(),taskSaved.getTitle(),taskSaved.getDescription());
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    public PagedTaskResponseDTO getTasks(int page, int limit){
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Task> taskPage = taskRepository.findAll(pageable);

        List<TaskResponseDTO> data = taskPage.getContent().stream()
                .map((task)-> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription()))
                .toList();

        return new PagedTaskResponseDTO(data, page, limit, taskPage.getTotalElements());
    }


}
