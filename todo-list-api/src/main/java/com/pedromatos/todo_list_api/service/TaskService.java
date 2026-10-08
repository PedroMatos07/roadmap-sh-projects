package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.dto.PagedTaskResponseDTO;
import com.pedromatos.todo_list_api.dto.TaskRequestDTO;
import com.pedromatos.todo_list_api.dto.TaskResponseDTO;
import com.pedromatos.todo_list_api.exception.ForbiddenException;
import com.pedromatos.todo_list_api.exception.ResourceNotFoundException;
import com.pedromatos.todo_list_api.model.Task;
import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.repository.TaskRepository;
import com.pedromatos.todo_list_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO requestDTO, String userEmail){
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task newTask = new Task(null, requestDTO.title(),requestDTO.description(), owner);

        Task taskSaved = taskRepository.save(newTask);

        return new TaskResponseDTO(taskSaved.getId(),taskSaved.getTitle(),taskSaved.getDescription());
    }

    public TaskResponseDTO updateTask(Long id,TaskRequestDTO requestDTO, String userEmail){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new ForbiddenException("You are not the owner of this task");
        }

        task.setDescription(requestDTO.description());
        task.setTitle(requestDTO.title());
        Task taskSaved = taskRepository.save(task);
        return new TaskResponseDTO(taskSaved.getId(),taskSaved.getTitle(),taskSaved.getDescription());
    }

    public void deleteTask(Long id, String email){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if(!task.getUser().getEmail().equals(email)){
            throw new ForbiddenException("You are not the owner of this task");
        }
        
        taskRepository.delete(task);
    }

    public PagedTaskResponseDTO getTasks(int page, int limit, String userEmail){
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Task> taskPage = taskRepository.findByUser_Email(userEmail, pageable);

        List<TaskResponseDTO> data = taskPage.getContent().stream()
                .map((task)-> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription()))
                .toList();

        return new PagedTaskResponseDTO(data, page, limit, taskPage.getTotalElements());
    }


}
