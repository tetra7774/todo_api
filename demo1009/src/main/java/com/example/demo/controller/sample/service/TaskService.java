package com.example.demo.controller.sample.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Service;
import com.example.demo.repository.task.TaskRecord;
import com.example.demo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskEntity find(Long taskID) {
        return taskRepository.select(taskID)
            .map(record -> new TaskEntity(record.getId(), record.getTitle())).orElseThrow(() -> new TaskEntityNotFoundException(taskID));
       
    }

    public TaskEntity create(String title) {
        var record = new TaskRecord(null, title);
        taskRepository.insert(record);
        return new TaskEntity(record.getId(), record.getTitle());
    }

    public List<TaskEntity> find(int limit, long offset) {
        return taskRepository.selectList(limit, offset)
        .stream()
        .map(record -> new TaskEntity(record.getId(), record.getTitle()))
        .collect(Collectors.toList());

    }

    public TaskEntity update(Long taskID, @NotNull @Size(min = 1, max = 256) String title) {
        taskRepository.select(taskID).orElseThrow(() -> new TaskEntityNotFoundException(taskID));
        taskRepository.update(new TaskRecord(taskID, title));
        return find(taskID);
    
    }

    public void delete(Long taskID) {
        taskRepository.select(taskID).orElseThrow(() -> new TaskEntityNotFoundException(taskID));
        taskRepository.delete(taskID);
    }


}
