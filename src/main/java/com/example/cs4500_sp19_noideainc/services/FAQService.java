package com.example.cs4500_sp19_noideainc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.cs4500_sp19_noideainc.repositories.FAQRepository;
import com.example.cs4500_sp19_noideainc.repositories.PagedFAQRepository;



@RestController
@CrossOrigin(origins="*")
public class FAQService {
	@Autowired
	FAQRepository faqRepository;
	@Autowired
    PagedFAQRepository pagedRepository;
	
	@GetMapping("/api/faqs/paged")
    public Page<FrequentlyAskedQuestion> findPagedFAQs(
            @RequestParam(name="page", required=false) Integer page,
            @RequestParam(name="count", required=false) Integer count) {
        if(page == null) {
            page = 0;
        }
        if(count == null) {
            count = 10;
        }
        Pageable p = PageRequest.of(page, count);
        return pagedRepository.findAll(p);
    }
	
	@GetMapping("/api/faqs")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions() {
		return faqRepository.findAllFrequentlyAskedQuestions();
	}
	@GetMapping("/api/faqs/{id}")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(
			@PathVariable("id") Integer id) {
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
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswerByFAQId(
			@PathVariable("faqId") Integer id) {
		return faqRepository.findAllFrequentlyAskedAnswersByFAQId(id);
	}
	
	@GetMapping("/api/faqs/filtered")
    public List<FrequentlyAskedQuestion> filterFAQs(
            @RequestParam(name="title", required=false) String title,
            @RequestParam(name="question", required=false) String question) {
        if(question != null && title != null) {
            return faqRepository.filterFAQs(title, question);
        }
        return faqRepository.findAllFrequentlyAskedQuestions();
    }
}