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
import org.springframework.web.bind.annotation.RestController;


import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedAnswer;
import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FrequentlyAskedQuestionRepository;

@RestController
@CrossOrigin(origins = "*")
public class FrequentlyAskedQuestionService {
	@Autowired
	FrequentlyAskedQuestionRepository faqRepository;
	
	@GetMapping("/api/faqs")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestion() {
		return faqRepository.findAllFrequentlyAskedQuestions();
	}
	
	@GetMapping("/api/faqs/{faqId}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("faqId") Integer id) {
		return faqRepository.findFrequentlyAskedQuestionById(id);
	}
	
	@PostMapping("/api/faqs")
	public FrequentlyAskedQuestion createFrequentlyAskedQuestion(@RequestBody FrequentlyAskedQuestion faq) {
		return faqRepository.save(faq);
	}
	
	@PutMapping("/api/faqs/{faqId}")
	public FrequentlyAskedQuestion updateFrequentlyAskedQuestion(
			@PathVariable("faqId") Integer id,
			@RequestBody FrequentlyAskedQuestion faqUpdates) {
		FrequentlyAskedQuestion faq = faqRepository.findFrequentlyAskedQuestionById(id);
		faq.setQuestion(faqUpdates.getQuestion());
		return faqRepository.save(faq);
	}
	
	@DeleteMapping("/api/faqs/{faqId}")
	public void deleteFrequentlyAskedQuestion(
			@PathVariable("faqId") Integer id) {
		faqRepository.deleteById(id);
	}
	
	@GetMapping("/api/faqs/{faqId}/answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswersByFAQId(
			@PathVariable("faqId") Integer id) {
		return faqRepository.findAllFrequentlyAskedAnswersByFAQId(id);
	}
}
