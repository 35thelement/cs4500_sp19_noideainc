package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedAnswer;

//This is the repository for FrequentlyAskedAnswer class, which 
//will implement all database operations related the FrequentlyAskedAnswer Data Model
public interface FAQAnswerRepository extends CrudRepository<FrequentlyAskedAnswer, Integer> {

	// find all answers for FAQs in the table
	@Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswers();
	
	// find the specific answer by given ID
	@Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.id=:id")
	public FrequentlyAskedAnswer findFrequentlyAskedAnswerById(@Param("id") Integer id);
	
	
}