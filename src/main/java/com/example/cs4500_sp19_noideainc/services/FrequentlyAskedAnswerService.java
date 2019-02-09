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

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedAnswer;
import com.example.cs4500_sp19_noideainc.repositories.FrequentlyAskedAnswerRepository;

@RestController
public class FrequentlyAskedAnswerService {
	@Autowired
	FrequentlyAskedAnswerRepository frequentlyAskedAnswerRepository;
	@GetMapping("/api/faqAnswers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswer() {
		return frequentlyAskedAnswerRepository.findAllFrequentlyAskedAnswers();
	}
	@GetMapping("/api/faqAnswers/{answerId}")
	public FrequentlyAskedAnswer findFrequentlyAskedAnswerById(
			@PathVariable("answerId") Integer id) {
		return frequentlyAskedAnswerRepository.findFrequentlyAskedAnswerById(id);
	}
	@PostMapping("/api/faqAnswers")
	public FrequentlyAskedAnswer createFrequentlyAskedAnswer(@RequestBody FrequentlyAskedAnswer answer) {
		return frequentlyAskedAnswerRepository.save(answer);
	}
	@PutMapping("/api/faqAnswers/{answerId}")
	public FrequentlyAskedAnswer updateFrequentlyAskedAnswer(
			@PathVariable("answerId") Integer id,
			@RequestBody FrequentlyAskedAnswer frequentlyAskedAnswerUpdates) {
		FrequentlyAskedAnswer answer = frequentlyAskedAnswerRepository.findFrequentlyAskedAnswerById(id);
		answer.setAnswer(frequentlyAskedAnswerUpdates.getAnswer());
		return frequentlyAskedAnswerRepository.save(answer);
	}
	@DeleteMapping("/api/faqAnswers/{answerId}")
	public void deleteFrequentlyAskedAnswer(
			@PathVariable("answerId") Integer id) {
		frequentlyAskedAnswerRepository.deleteById(id);
	}

}
