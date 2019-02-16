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
import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.repositories.ServiceAnswerRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins="*")
public class ServiceAnswerService {
	@Autowired
	ServiceAnswerRepository repository;
	@GetMapping("/api/service-answers")
	public List<ServiceAnswer> findAllServiceAnswers() {
		return repository.findAllServiceAnswer();
	}
	@GetMapping("/api/service-answers/{id}")
	public ServiceAnswer findServiceAnswerById(
			@PathVariable("id") Integer id) {
		return repository.findServiceAnswerById(id);
	}
	
	@PostMapping("/api/service-answers")
	public ServiceAnswer createServiceAnswer(@RequestBody ServiceAnswer serviceAnswer) {
		return repository.save(serviceAnswer);
	}
	@PutMapping("/api/service-answers/{id}")
	public ServiceAnswer updateServiceCategory(
			@PathVariable("id") Integer id,
			@RequestBody ServiceAnswer serviceAnswerUpdate) {
		ServiceAnswer serviceAnswer = repository.findServiceAnswerById(id);
		serviceAnswer.setChoiceAnswer(serviceAnswerUpdate.getChoiceAnswer());
		serviceAnswer.setMaxRangeAnswer(serviceAnswerUpdate.getMaxRangeAnswer());
		serviceAnswer.setMinRangeAnswer(serviceAnswerUpdate.getMinRangeAnswer());
		serviceAnswer.setTrueFalseAnswer(serviceAnswerUpdate.getTrueFalseAnswer());
		return repository.save(serviceAnswer);
	}
	@DeleteMapping("/api/service-answers/{serviceAnswerId}")
	public void deleteServiceAnswer(
			@PathVariable("serviceAnswerId") Integer id) {
		    repository.deleteById(id);
	}

	
	
}