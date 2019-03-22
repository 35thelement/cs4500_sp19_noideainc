package com.example.cs4500_sp19_noideainc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.utils.ServiceSearch;

public class ServiceSearchTest {

	private Service yardWork;
	private ServiceQuestion q1;
	private ServiceQuestion q2;
	private ServiceQuestion q3;
	//david bowie
	private User prov1;
	//charlie sheen
	private User prov2;
	//freddie mercury
	private User prov3;

	@Test
	public void test_yardwork_search_tools_true() {
		//System.out.println("Service: " + yardWork.getTitle());
		/*for (User p : yardWork.getProviders()) {
			System.out.println();
			System.out.println("Provider: " + p.getFirstName() + " " + p.getLastName());
			for (ServiceAnswer a : p.getServiceAnswers()) {
				System.out.println("Question: " + a.getServiceQuestion().getTitle());
				System.out.println("Answer: " + a.getChoiceAnswer() + " " + a.getMinRangeAnswer() + ", "
						+ a.getMaxRangeAnswer() + " " + a.getTrueFalseAnswer());
			}
		}*/

		SearchCriteria yardCriteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriteriaAns = new ArrayList<SearchPredicate>();
		ServiceAnswer noTools = new ServiceAnswer();
		noTools.setTrueFalseAnswer(true);
		SearchPredicate q3Pred = new SearchPredicate(q3, noTools);
		searchCriteriaAns.add(q3Pred);
		yardCriteria.setCriteria(searchCriteriaAns);

		/*System.out.println("RESULT: ");
		for (User u : ServiceSearch.searchForProviders(yardWork, yardCriteria)) {
			System.out.println(u.getFirstName() + " " + u.getLastName());
		}*/
		
		ArrayList<User> output = new ArrayList<User>();
		output.add(prov1);
		output.add(prov3);
		// does test
		 assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria), output);

	}
	
	@Test
	public void test_yardwork_search_availability_both() {
		SearchCriteria yardCriteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriteriaAns = new ArrayList<SearchPredicate>();
		ServiceAnswer availableBothDays = new ServiceAnswer();
		availableBothDays.setChoiceAnswer(2);
		SearchPredicate availabilityPred = new SearchPredicate(q2, availableBothDays);
		searchCriteriaAns.add(availabilityPred);
		yardCriteria.setCriteria(searchCriteriaAns);

		ArrayList<User> output = new ArrayList<User>();
		output.add(prov3);
		//does test
		assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria), output);

	}
	@Test
	public void test_yardwork_search_range_4() {
		init();
		System.out.println(yardWork);
		System.out.println("Service: " + yardWork.getTitle());
		for (User p : yardWork.getProviders()) {
			System.out.println();
			System.out.println("Provider: " + p.getFirstName() + " " + p.getLastName());
			for (ServiceAnswer a : p.getServiceAnswers()) {
				System.out.println("Question: " + a.getServiceQuestion().getTitle());
				System.out.println("Answer: " + a.getChoiceAnswer() + " " + a.getMinRangeAnswer() + ", "
						+ a.getMaxRangeAnswer() + " " + a.getTrueFalseAnswer());
			}
		}
		
		SearchCriteria yardCriteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriteriaAns = new ArrayList<SearchPredicate>();
		ServiceAnswer acres = new ServiceAnswer();
		acres.setMinRangeAnswer(4);
		acres.setMaxRangeAnswer(4);
		SearchPredicate q1Pred = new SearchPredicate(q1, acres);
		searchCriteriaAns.add(q1Pred);
		yardCriteria.setCriteria(searchCriteriaAns);

		System.out.println("RESULT: ");
		for (User u : ServiceSearch.searchForProviders(yardWork, yardCriteria)) {
			System.out.println(u.getFirstName() + " " + u.getLastName());
		}
		// does test
		ArrayList<User> output = new ArrayList<User>();
		output.add(prov1);
		output.add(prov2);
		assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria), output);
		// fail("Not yet implemented");

	}
	
	//Test that providers are ordered correctly when more than 1 provider matches the search criteria
	@Test
	public void test_yardwork_search_tools_true_acres_4() {
		SearchCriteria yardCriteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriterians = new ArrayList<SearchPredicate>();
		
		ServiceAnswer acres = new ServiceAnswer();
		acres.setMinRangeAnswer(4);
		acres.setMaxRangeAnswer(4);
		SearchPredicate q1Pred = new SearchPredicate(q1, acres);
		
		ServiceAnswer tools = new ServiceAnswer();
		tools.setTrueFalseAnswer(true);
		SearchPredicate q3Pred = new SearchPredicate(q3, tools);
		
		searchCriterians.add(q1Pred);
		searchCriterians.add(q3Pred);
		
		yardCriteria.setCriteria(searchCriterians);
		
		ArrayList<User> output = new ArrayList<User>();
		output.add(prov1);
		output.add(prov2);
		output.add(prov3);
		
		assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria), output);
		
		
	}
	
	//Test that false is returned if the providers are out of order, even if all the correct providers are present
		@Test
		public void test_yardwork_search_tools_true_acres_4_incorrect_order() {
			SearchCriteria yardCriteria = new SearchCriteria();
			ArrayList<SearchPredicate> searchCriterians = new ArrayList<SearchPredicate>();
			
			ServiceAnswer acres = new ServiceAnswer();
			acres.setMinRangeAnswer(4);
			acres.setMaxRangeAnswer(4);
			SearchPredicate q1Pred = new SearchPredicate(q1, acres);
			
			ServiceAnswer tools = new ServiceAnswer();
			tools.setTrueFalseAnswer(true);
			SearchPredicate q3Pred = new SearchPredicate(q3, tools);
			
			searchCriterians.add(q1Pred);
			searchCriterians.add(q3Pred);
			
			yardCriteria.setCriteria(searchCriterians);
			
			ArrayList<User> output = new ArrayList<User>();
			output.add(prov2);
			output.add(prov1);
			output.add(prov3);
			
			assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria).equals(output), false);
			
			
		}
	

	@Test
	public void test_yardwork_search_range_one_million() {
		init();
		System.out.println(yardWork);
		System.out.println("Service: " + yardWork.getTitle());
		for (User p : yardWork.getProviders()) {
			System.out.println();
			System.out.println("Provider: " + p.getFirstName() + " " + p.getLastName());
			for (ServiceAnswer a : p.getServiceAnswers()) {
				System.out.println("Question: " + a.getServiceQuestion().getTitle());
				System.out.println("Answer: " + a.getChoiceAnswer() + " " + a.getMinRangeAnswer() + ", "
						+ a.getMaxRangeAnswer() + " " + a.getTrueFalseAnswer());
			}
		}
		
		SearchCriteria yardCriteria = new SearchCriteria();
		ArrayList<SearchPredicate> searchCriteriaAns = new ArrayList<SearchPredicate>();
		ServiceAnswer acres = new ServiceAnswer();
		acres.setMinRangeAnswer(1000000);
		acres.setMaxRangeAnswer(1000000);
		SearchPredicate q1Pred = new SearchPredicate(q1, acres);
		searchCriteriaAns.add(q1Pred);
		yardCriteria.setCriteria(searchCriteriaAns);

		System.out.println("RESULT: ");
		for (User u : ServiceSearch.searchForProviders(yardWork, yardCriteria)) {
			System.out.println(u.getFirstName() + " " + u.getLastName());
		}
		// does test
		ArrayList<User> output = new ArrayList<User>();
		assertEquals(ServiceSearch.searchForProviders(yardWork, yardCriteria), output);
		// fail("Not yet implemented");

	}
	
	

	// Does all test setup before each test case
	@BeforeEach
	public void init() {
		// YARD WORK SERVICE
		this.yardWork = new Service();
		this.yardWork.setTitle("Yard Work");
		// YARD WORK QUESTIONS
		q1 = new ServiceQuestion();
		q2 = new ServiceQuestion();
		q3 = new ServiceQuestion();
		// updating YARD WORK questions
		q1.setTitle("Acres");
		q1.setQuestion("How many acres is the property?");
		q1.setType("RANGE");
		q1.setId(1);
		q2.setTitle("Availability");
		q2.setQuestion("What days are you available");
		q2.setType("MULTIPLE_CHOICE");
		q2.setChoices("weekdays, weekends, both");
		q2.setId(2);
		q3.setTitle("Tools");
		q3.setQuestion("Will you provide you're own tools");
		q3.setType("TRUE_FALSE");
		q3.setId(3);

		// YARD WORK PROVIDERS
		prov1 = new User();
		prov2 = new User();
		prov3 = new User();
		// add YARD WORK to providers' list of services
		ArrayList<Service> servlist = new ArrayList<Service>();
		servlist.add(this.yardWork);
		prov1.setServices(servlist);
		prov2.setServices(servlist);
		prov3.setServices(servlist);
		// update provider names
		prov1.setFirstName("David");
		prov2.setFirstName("Michael");
		prov3.setFirstName("Fredie");
		prov1.setLastName("Bowie");
		prov2.setLastName("Sheen");
		prov3.setLastName("Mercury");
		// adds providers to YARD WORK list of providers
		ArrayList<User> providers = new ArrayList<User>();
		providers.add(prov1);
		providers.add(prov2);
		providers.add(prov3);
		this.yardWork.setProviders(providers);
		// UPDATING PROVIDER ANSWERS TO YARD WORK QUESTIONS
		// list of answer lists
		ArrayList<ServiceAnswer> prov1ans = new ArrayList<ServiceAnswer>();
		ArrayList<ServiceAnswer> prov2ans = new ArrayList<ServiceAnswer>();
		ArrayList<ServiceAnswer> prov3ans = new ArrayList<ServiceAnswer>();
		// adds answer lists
		prov1.setServiceAnswers(prov1ans);
		prov2.setServiceAnswers(prov2ans);
		prov3.setServiceAnswers(prov3ans);

		// adding answers to YARD WORK question 1
		// provider 1 answer 1
		ServiceAnswer prov1q1ans = new ServiceAnswer();
		prov1q1ans.setId(11);
		prov1q1ans.setProvider(prov1);
		prov1q1ans.setMinRangeAnswer(1);
		prov1q1ans.setMaxRangeAnswer(5);
		prov1q1ans.setServiceQuestion(q1);
		prov1ans.add(prov1q1ans);
		// provider 2 answer 1
		ServiceAnswer prov2q1ans = new ServiceAnswer();
		prov2q1ans.setId(12);
		prov2q1ans.setProvider(prov2);
		prov2q1ans.setMinRangeAnswer(3);
		prov2q1ans.setMaxRangeAnswer(6);
		prov2q1ans.setServiceQuestion(q1);
		prov2ans.add(prov2q1ans);
		// provider 3 answer 1
		ServiceAnswer prov3q1ans = new ServiceAnswer();
		prov3q1ans.setId(13);
		prov3q1ans.setProvider(prov3);
		prov3q1ans.setMinRangeAnswer(1);
		prov3q1ans.setMaxRangeAnswer(2);
		prov3q1ans.setServiceQuestion(q1);
		prov3ans.add(prov3q1ans);

		// adding answers to YARD WORK question 2
		// provider 1 answer 2
		ServiceAnswer prov1q2ans = new ServiceAnswer();
		prov1q2ans.setId(21);
		prov1q2ans.setProvider(prov1);
		prov1q2ans.setChoiceAnswer(0);
		prov1q2ans.setServiceQuestion(q2);
		prov1ans.add(prov1q2ans);
		// provider 2 answer 2
		ServiceAnswer prov2q2ans = new ServiceAnswer();
		prov2q2ans.setId(22);
		prov2q2ans.setProvider(prov2);
		prov2q2ans.setChoiceAnswer(1);
		prov2q2ans.setServiceQuestion(q2);
		prov2ans.add(prov2q2ans);
		// provider 3 answer 2
		ServiceAnswer prov3q2ans = new ServiceAnswer();
		prov3q2ans.setId(23);
		prov3q2ans.setProvider(prov3);
		prov3q2ans.setChoiceAnswer(2);
		prov3q2ans.setServiceQuestion(q2);
		prov3ans.add(prov3q2ans);

		// adding answers to YARD WORK question 3
		// provider 1 answer 3
		ServiceAnswer prov1q3ans = new ServiceAnswer();
		prov1q3ans.setId(31);
		prov1q3ans.setProvider(prov1);
		prov1q3ans.setTrueFalseAnswer(true);
		prov1q3ans.setServiceQuestion(q3);
		prov1ans.add(prov1q3ans);
		// provider 2 answer 3
		ServiceAnswer prov2q3ans = new ServiceAnswer();
		prov2q3ans.setId(32);
		prov2q3ans.setProvider(prov2);
		prov2q3ans.setTrueFalseAnswer(false);
		prov2q3ans.setServiceQuestion(q3);
		prov2ans.add(prov2q3ans);
		// provider 3 answer 3
		ServiceAnswer prov3q3ans = new ServiceAnswer();
		prov3q3ans.setId(33);
		prov3q3ans.setProvider(prov3);
		prov3q3ans.setTrueFalseAnswer(true);
		prov3q3ans.setServiceQuestion(q3);
		prov3ans.add(prov3q3ans);
	}
}
