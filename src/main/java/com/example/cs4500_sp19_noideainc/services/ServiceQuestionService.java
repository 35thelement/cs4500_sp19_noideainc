package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.repositories.ServiceQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class ServiceQuestionService {
  @Autowired
  ServiceQuestionRepository repository;
  @GetMapping("/api/service-questions")
  public List<ServiceQuestion> findAllServiceQuestions() {
    return repository.findAllServiceQuestions();
  }
  @GetMapping("/api/service-questions/{id}")
  public ServiceQuestion findServiceQuestionById(
          @PathVariable("id") Integer id) {
    return repository.findServiceQuestionById(id);
  }
}