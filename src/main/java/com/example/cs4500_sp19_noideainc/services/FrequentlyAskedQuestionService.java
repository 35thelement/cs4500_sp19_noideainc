package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FrequentlyAskedQuestionRepository;

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
}
