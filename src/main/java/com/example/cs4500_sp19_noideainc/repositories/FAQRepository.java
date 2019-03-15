package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedAnswer;
import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;

// This is the repository for FrequentlyAskedQuestion (FAQ) class, which 
// will implement all database operations related the FrequentlyAskedQuestion Data Model
public interface FAQRepository extends CrudRepository<FrequentlyAskedQuestion, Integer> {
	
	// find all FAQs in the table
	@Query(value="SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion")
	public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions();
	
	// find the specific FAQ by given ID
	@Query(value="SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion WHERE frequentlyAskedQuestion.id=:id")
	public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(@Param("id") Integer id);
	
	// find all answers that belongs to one FAQ by given FAQ ID
	@Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.frequentlyAskedQuestion.id=:FAQid")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswersByFAQId(@Param("FAQid") Integer FAQid);
	
	// find filtered FAQ by title and question
	@Query("SELECT faq FROM FrequentlyAskedQuestion faq WHERE faq.title LIKE %:title% AND faq.question LIKE %:question%")
	public List<FrequentlyAskedQuestion> filterFAQs(
	       @Param("title") String title,
	       @Param("question") String question);
}

