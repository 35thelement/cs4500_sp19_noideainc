package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceSpecificQuestion;
import com.example.cs4500_sp19_noideainc.repositories.ServiceAnswerRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceSpecificQuestionRepository;

@RestController
public class ServiceSpecificQuestionService {
	@Autowired
	ServiceSpecificQuestionRepository serviceSpecificQuestionRepository;
	@GetMapping("/api/questions")
	public List<ServiceSpecificQuestion> findAllServiceSpecificQuestion() {
		return serviceSpecificQuestionRepository.findAllServiceSpecificQuestions();
	}
	@GetMapping("/api/questions/{serviceSpecificQuestionId}")
	public ServiceSpecificQuestion findServiceSpecificQuestionById(
			@PathVariable("serviceAnswerId") Integer id) {
		return serviceSpecificQuestionRepository.findServiceSpecificQuestionById(id);
	}

}
