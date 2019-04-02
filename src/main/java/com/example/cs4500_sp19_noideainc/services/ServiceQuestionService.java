package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.repositories.ServiceQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class ServiceQuestionService {
  @Autowired
  ServiceQuestionRepository questionRepository;
  
  @GetMapping("/api/service-questions")
  public List<ServiceQuestion> findAllServiceQuestions() {
    return questionRepository.findAllServiceQuestions();
  }
  
  @GetMapping("/api/service-questions/{questionId}")
  public ServiceQuestion findServiceQuestionById(
          @PathVariable("questionId") Integer questionId) {
    return questionRepository.findServiceQuestionById(questionId);
  }
  
  @GetMapping("/api/service-questions/service/{serviceId}")
  public List<ServiceQuestion> findServiceQuestionsByServiceId(
          @PathVariable("serviceId") Integer serviceId) {
    return questionRepository.findServiceQuestionsByServiceId(serviceId);
  }
  
  @PostMapping("/api/service-questions")
  public ServiceQuestion createServiceQuestion(@RequestBody ServiceQuestion serviceQuestion) {
	  return questionRepository.save(serviceQuestion);
  }
  
  @PutMapping("api/service-questions/{questionId}")
  public ServiceQuestion updateServiceQuestion(@PathVariable("questionId") Integer id, @RequestBody ServiceQuestion questionUpdates) {
	  ServiceQuestion serviceQuestion = questionRepository.findServiceQuestionById(id);
	  serviceQuestion.setTitle(questionUpdates.getTitle());
	  serviceQuestion.setQuestion(questionUpdates.getQuestion());
	  serviceQuestion.setType(questionUpdates.getType());
	  serviceQuestion.setChoices(questionUpdates.getChoices());
	  
	  return questionRepository.save(serviceQuestion);
  }
  
  @DeleteMapping("api/service-questions/{questionId}")
  public void deleteQuestion(@PathVariable("questionId") Integer id) {
	  questionRepository.deleteById(id);
  }
  
}