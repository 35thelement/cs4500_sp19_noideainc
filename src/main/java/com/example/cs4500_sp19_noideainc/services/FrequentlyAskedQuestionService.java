package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
