package com.example.cs4500_sp19_noideainc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.utils.ServiceSearch;
import com.example.cs4500_sp19_noideainc.models.QuestionType;

public class ServiceSearchTest {

	@Test
	public void testBase() {
		Service service = new Service();
		ServiceQuestion q1 = new ServiceQuestion();
		ServiceQuestion q2 = new ServiceQuestion();
		ServiceQuestion q3 = new ServiceQuestion();
		SearchCriteria criteria = new SearchCriteria();
		User prov1 = new User();
		User prov2 = new User();
		User prov3 = new User();
		ArrayList<Service> servlist = new ArrayList<Service>();
		servlist.add(service);
		prov1.setServices(servlist);
		prov2.setServices(servlist);
		prov3.setServices(servlist);
		ServiceAnswer ans1 = new ServiceAnswer();
		ServiceAnswer ans2 = new ServiceAnswer();
		ServiceAnswer ans3 = new ServiceAnswer();
		ArrayList<User> output = new ArrayList<User>();
		assertEquals(output.equals(output), true);
		//fail("Not yet implemented");

	}

	@Test
	public void test1() {
		//Creates Service
		Service service = new Service();
		service.setTitle("Yard Work");
		//Creates Questions
		ServiceQuestion q1 = new ServiceQuestion();
		ServiceQuestion q2 = new ServiceQuestion();
		ServiceQuestion q3 = new ServiceQuestion();
		//Sets Question information
		q1.setQuestion("How many acres is the property?");
		q1.setType(QuestionType.RANGE);
		q2.setQuestion("What days are you available");
		q2.setType(QuestionType.MULTIPLE_CHOICE);
		q2.setChoices("weekdays, weekends, both");
		q3.setQuestion("Will you provide you're own tools");
		q3.setType(QuestionType.TRUE_FALSE);
		//Creates Criteria
		SearchCriteria criteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriteriaAns = new ArrayList<SearchPredicate>();
		//Creates Answer for Criteria
		ServiceAnswer searchAns1 = new ServiceAnswer();
		searchAns1.setMinRangeAnswer(2);
		searchAns1.setMaxRangeAnswer(4);
		SearchPredicate pred1 = new SearchPredicate(q1,searchAns1);
		ServiceAnswer searchAns2 = new ServiceAnswer();
		searchAns2.setChoiceAnswer(1);
		SearchPredicate pred2 = new SearchPredicate(q2,searchAns2);
		ServiceAnswer searchAns3 = new ServiceAnswer();
		searchAns3.setTrueFalseAnswer(false);
		SearchPredicate pred3 = new SearchPredicate(q3,searchAns3);
		//Adds answer to criteria
		searchCriteriaAns.add(pred1);
		searchCriteriaAns.add(pred2);
		searchCriteriaAns.add(pred3);
		criteria.setCriteria(searchCriteriaAns);
		//Creates users 
		User prov1 = new User();
		User prov2 = new User();
		User prov3 = new User();
		//sets services for users
		ArrayList<Service> servlist = new ArrayList<Service>();
		servlist.add(service);
		prov1.setServices(servlist);
		prov2.setServices(servlist);
		prov3.setServices(servlist);
		//Creates answer for users, still needs to be fleshed out
		ServiceAnswer prov1ans1 = new ServiceAnswer();
		ServiceAnswer prov1ans2 = new ServiceAnswer();
		ServiceAnswer prov1ans3 = new ServiceAnswer();
		ServiceAnswer prov2ans1 = new ServiceAnswer();
		ServiceAnswer prov2ans2 = new ServiceAnswer();
		ServiceAnswer prov2ans3 = new ServiceAnswer();
		ServiceAnswer prov3ans1 = new ServiceAnswer();
		ServiceAnswer prov3ans2 = new ServiceAnswer();
		ServiceAnswer prov3ans3 = new ServiceAnswer();
		//list of all users to be associated with the service
		ArrayList<User> output = new ArrayList<User>();
		output.add(prov1);
		output.add(prov2);
		output.add(prov3);
		//remove users for tests 
		service.setProviders(output);
		//list of answer lists, still beed to add individual answers
		ArrayList<ServiceAnswer> prov1ans = new ArrayList<ServiceAnswer>();
		ArrayList<ServiceAnswer> prov2ans = new ArrayList<ServiceAnswer>();
		ArrayList<ServiceAnswer> prov3ans = new ArrayList<ServiceAnswer>();
		//adds answer lists
		prov1.setServiceAnswers(prov1ans);
		prov2.setServiceAnswers(prov2ans);
		prov3.setServiceAnswers(prov3ans);
		//does test
		assertEquals(ServiceSearch.searchForProviders(service, criteria).equals(output), true);
		//fail("Not yet implemented");

	}
}
