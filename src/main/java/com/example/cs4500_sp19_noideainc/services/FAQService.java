package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FAQRepository;



@RestController
@CrossOrigin(origins="*")
public class FAQService {
	@Autowired
	FAQRepository repository;
	@GetMapping("/api/faqs")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions() {
		return repository.findAllFrequentlyAskedQuestions();
	}
	@GetMapping("/api/faqs/{id}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findFrequentlyAskedQuestionById(id);
	}
}