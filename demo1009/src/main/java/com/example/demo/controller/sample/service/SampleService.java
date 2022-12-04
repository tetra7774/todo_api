package com.example.demo.controller.sample.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.SampleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {

        private final SampleRepository repository;

        public SampleEntity find(){
            var entity = repository.select();
            return new SampleEntity(entity.getContents());
        }
}
