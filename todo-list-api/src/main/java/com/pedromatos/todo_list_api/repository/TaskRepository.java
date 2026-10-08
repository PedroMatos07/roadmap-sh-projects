package com.pedromatos.todo_list_api.repository;

import com.pedromatos.todo_list_api.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUser_Email(String email, Pageable pageable);
}
