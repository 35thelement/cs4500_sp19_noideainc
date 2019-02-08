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
import com.example.cs4500_sp19_noideainc.repositories.ServiceAnswerRepository;

@RestController
public class ServiceAnswerService {
	@Autowired
	ServiceAnswerRepository serviceAnswerRepository;
	@GetMapping("/api/answers")
	public List<ServiceAnswer> findAllServiceAnswer() {
		return serviceAnswerRepository.findAllServiceAnswer();
	}
	@GetMapping("/api/answers/{serviceAnswerId}")
	public ServiceAnswer findServiceAnswerById(
			@PathVariable("serviceAnswerId") Integer id) {
		return serviceAnswerRepository.findServiceAnswerById(id);
	}

}
