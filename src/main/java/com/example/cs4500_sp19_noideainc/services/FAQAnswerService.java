package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedAnswer;
import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FAQAnswerRepository;


@RestController
@CrossOrigin(origins="*")
public class FAQAnswerService {
	@Autowired
	FAQAnswerRepository repository;
	@GetMapping("/api/faq-answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedQuestions() {
		return repository.findAllFrequentlyAskedAnswers();
	}
	@GetMapping("/api/faq-answers/{id}")
	public FrequentlyAskedAnswer findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
		return repository.findFrequentlyAskedAnswerById(id);
	}
	@PostMapping("/api/faq-answers")
	public FrequentlyAskedAnswer createFrequentlyAskedAnswer(@RequestBody FrequentlyAskedAnswer answer) {
		return repository.save(answer);
	}
	@PutMapping("/api/faq-answers/{answerId}")
	public FrequentlyAskedAnswer updateFrequentlyAskedAnswer(
			@PathVariable("answerId") Integer id,
			@RequestBody FrequentlyAskedAnswer frequentlyAskedAnswerUpdates) {
		FrequentlyAskedAnswer answer = repository.findFrequentlyAskedAnswerById(id);
		answer.setAnswer(frequentlyAskedAnswerUpdates.getAnswer());
		return repository.save(answer);
	}
	@DeleteMapping("/api/faq-answers/{answerId}")
	public void deleteFrequentlyAskedAnswer(
			@PathVariable("answerId") Integer id) {
		repository.deleteById(id);
	}
	
	@GetMapping("/api/faq-answers/filtered")
    public List<FrequentlyAskedAnswer> filterFAAs(
            @RequestParam(name="answer", required=false) String answer) {
        if(answer != null) {
            return repository.filterFAAs(answer);
        }
        return repository.findAllFrequentlyAskedAnswers();
    }
}