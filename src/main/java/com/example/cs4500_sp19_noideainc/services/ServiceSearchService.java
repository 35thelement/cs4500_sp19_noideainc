package com.example.cs4500_sp19_noideainc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.cs4500_sp19_noideainc.models.User;
//fix start later
import com.example.cs4500_sp19_noideainc.utils.*;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceQuestionRepository;;
@RestController
@CrossOrigin(origins="*")
public class ServiceSearchService {
	@Autowired
    ServiceRepository serviceRepository;
	@Autowired
	ServiceQuestionRepository questionRepository;
	ServiceQuestionService questionService = new ServiceQuestionService();
	
	@GetMapping("/api/service-search/{serviceID}/{questionIDs}/{criteria}")
	public List<User> findAllProviders(@PathVariable("serviceID") Integer serviceID,
			@PathVariable("questionIDs") String questionIDs,
            @PathVariable("criteria") String criteria) {
		
		System.out.println("GOT HERE");
		System.out.println(serviceID);
		System.out.println(criteria);
		
		//retrieves list from url
		ArrayList<String> criteriaStringList = new ArrayList<String>(Arrays.asList(criteria.split(",")));
		ArrayList<String> questionIDsStringList = new ArrayList<String>(Arrays.asList(questionIDs.split(",")));
		//debugging print statements
		for(int i =0; i < criteriaStringList.size(); i ++) {
			System.out.println(criteriaStringList.get(i));
			System.out.println(questionIDsStringList.get(i));

		}
		//initializes arrays for 
		ArrayList<Integer> qIDs = new ArrayList<Integer>();
		ArrayList<Integer> crits = new ArrayList<Integer>();
		ArrayList<Integer> qIDsRange = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> critRANGE= new ArrayList<ArrayList<Integer>>();
		//retrieves answers from strings
		for(int i =0; i < criteriaStringList.size(); i ++) {
			//fix flag
			//- is a flag to indicate that this is a list within the list of answers,
			//this is to factor in for min max answers
			if(criteriaStringList.get(i).contains("-r")) {
				String temp = criteriaStringList.get(i).substring(2, criteriaStringList.get(i).length());
				if(temp.length() == 0) {
					continue;
				}
				qIDsRange.add(Integer.parseInt(questionIDsStringList.get(i)));
				ArrayList<String> tempRangeList = new ArrayList<String>(Arrays.asList(temp.split("_")));
				ArrayList<Integer> tempIntRangeList = new ArrayList<Integer>();
				for(int j =0; j < tempRangeList.size(); j++) {
					System.out.println(" range answers " + tempRangeList.get(j));
					
					tempIntRangeList.add(Integer.parseInt(tempRangeList.get(j)));
				}
				critRANGE.add(tempIntRangeList);
				System.out.println("RANGE");
				System.out.println(temp);
				continue;
			}
			//otherwise the answer can be added in as is
			crits.add(Integer.parseInt(criteriaStringList.get(i)));
			qIDs.add(Integer.parseInt(questionIDsStringList.get(i)));
		}
		// finds the service this is associated with
		System.out.println(serviceID);
		Service service = serviceRepository.findServiceById(serviceID);
		//initializes new criteria and list of predicates
		SearchCriteria searchCrit = new SearchCriteria();
		ArrayList<SearchPredicate> searchPreds = new ArrayList<SearchPredicate>();
		//loops through all true false and multiple choice
		for(int i = 0; i < qIDs.size(); i++) {
			//initializes temporary answer and question
			ServiceAnswer tempAnswer = new ServiceAnswer();
			//retrieves questions from the question service
			ServiceQuestion tempQuestion = questionRepository.findServiceQuestionById(qIDs.get(i));
			tempAnswer.setServiceQuestion(tempQuestion);
			//retrieves question type
			String type = tempQuestion.getType();
			//initializes new predicate
			SearchPredicate tempPred;
			switch(type) {
            case "TRUE_FALSE":
            //may want to change this to remove the question if -1
              if(crits.get(i) == -1) {
            	  
            	  //crits.remove(i);
            	  //qIDs.remove(i);
              }
              else {
            	  tempAnswer.setTrueFalseAnswer(true);
                  tempPred= new SearchPredicate(tempQuestion,tempAnswer);
                  searchPreds.add(tempPred);
              }
              //adds the predicate to the list

              break;
            case "MULTIPLE_CHOICE":
            	//could cause an error
            	if(crits.get(i)== -1) {
            		//crits.remove(i);
            		//qIDs.remove(i);
            	}
            	else {
            		tempAnswer.setChoiceAnswer(crits.get(i));
          			tempPred = new SearchPredicate(tempQuestion,tempAnswer);
          			searchPreds.add(tempPred);
            	}
            	//adds predicate to the list
               break;
              }
          }
		//loops through the range answers which could have a variable amount of answers
		for(int i = 0; i < qIDsRange.size(); i++) {
			System.out.println("QIDS :" + qIDsRange.get(i));
			//initializes min and max
			Integer min = null;
			Integer max = null;
			//initializes answer, gets questions and initializes predicate
			ServiceAnswer tempAnswer = new ServiceAnswer();
			ServiceQuestion tempQuestion =questionRepository.findServiceQuestionById(qIDsRange.get(i));
			tempAnswer.setServiceQuestion(tempQuestion);
			SearchPredicate tempPred;
			//loops through all range answers
			for(int j =0; j < critRANGE.get(i).size(); j++) {
				
				System.out.println(critRANGE.get(i).get(j));
				//loops through all answers in range answer
				//if -1 its not selected
				if(critRANGE.get(i).get(j) != -1) {
					if(min == null) {
						min = critRANGE.get(i).get(j);
					}
					if(max == null) {
						max = critRANGE.get(i).get(j);
					}
					if(min > critRANGE.get(i).get(j)) {
						min = critRANGE.get(i).get(j);
					}
					if(max < critRANGE.get(i).get(j)) {
						max = critRANGE.get(i).get(j);
					}
				}
			}
			//only if answers are not null do we add them in
			if(!(min == null)) {
				System.out.println("ADDS VAlUE");
				tempAnswer.setMinRangeAnswer(min);
				tempAnswer.setMaxRangeAnswer(max);
				tempPred = new SearchPredicate(tempQuestion,tempAnswer);
				System.out.println(tempPred.toString());
      			searchPreds.add(tempPred);
      			//resets all values if added
      			tempAnswer = null;
      			tempPred =null;
      			max = null;
      			min = null;
			}
				
				
			}
		//gets response and sends it back
		System.out.println("GOT TO THE END!!");
		searchCrit.setCriteria(searchPreds);
		System.out.println("Search Crit" + searchCrit.toString());
		List<User> response = ServiceSearch.searchForProviders(service, searchCrit);
		return response;

			
		}
	
	@GetMapping("/api/service-search/{serviceID}//")
	public List<User> findAllProviders(@PathVariable("serviceID") Integer serviceID) {
		return serviceRepository.findServiceById(serviceID).getProviders();
		
	}
		
	
		
		
		
	}

