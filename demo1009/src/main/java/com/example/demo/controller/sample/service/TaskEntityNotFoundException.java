package com.example.demo.controller.sample.service;

public class TaskEntityNotFoundException extends RuntimeException {

    private Long taskID;
    TaskEntityNotFoundException(Long taskID){

        super("TaskEnety(taskID = "+ taskID + ") is not found");
        this.taskID = taskID;

    }
    
}
