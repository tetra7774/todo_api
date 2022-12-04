package com.example.demo.controller.task;

import java.net.URI;
import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.TasksApi;
import com.example.demo.controller.sample.service.TaskEntity;
import com.example.demo.controller.sample.service.TaskService;
import com.example.demo.model.PageDTO;
import com.example.demo.model.TaskDTO;
import com.example.demo.model.TaskForm;
import com.example.demo.model.TaskListDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi{
    
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskID) {
        var entity = taskService.find(taskID);
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        var entity = taskService.create(form.getTitle());
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity.created(URI.create("/tasks/"+ entity.getId())).body(dto);
    }

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset){
        var entitylist = taskService.find(limit, offset);
        var dtoList = entitylist.stream().map(this::toTaskDTO).collect(Collectors.toList());
        var dto = new TaskListDTO();
        var pageDTO = new PageDTO();
        
        pageDTO.setLimit(limit);
        pageDTO.setOffset(offset);
        pageDTO.setSize(dtoList.size());

        dto.setResult(dtoList);
        dto.setPage(pageDTO);

        return ResponseEntity.ok(dto);
    }
    

    private TaskDTO toTaskDTO(TaskEntity taskEntity) {
        var taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setTitle(taskEntity.getTitle());
        return taskDTO;
    }

    @Override
    public ResponseEntity<TaskDTO> editTask(Long taskID, TaskForm taskForm) {
        var entity = taskService.update(taskID, taskForm.getTitle());
        var dto = toTaskDTO(entity);
        
        return ResponseEntity.ok(dto);

    }

    
    @Override
    public ResponseEntity<Void> deleteTask(Long taskID) {
        taskService.delete(taskID);
        return ResponseEntity.noContent().build();
    }
}
