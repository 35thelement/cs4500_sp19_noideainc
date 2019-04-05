package com.example.cs4500_sp19_noideainc.utils;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceSearch {



  public static List<User> searchForProviders(Service service, SearchCriteria criteria) {

    List<User> users = service.getProviders();
    
    List<SearchPredicate> preds = criteria.getCriteria();
    ArrayList<UserToInt> resutltsToInt= new ArrayList<UserToInt>();
    ArrayList<User> result = new ArrayList<User>();
    System.out.println("Amount of the users for this service " + users.size());

    for(User u : users) {
      //retrieves user answer and initializes rank to 0
      List<ServiceAnswer> answers = u.getServiceAnswers();
      int rank = 0;
      //loops through each search questions
      for(int k = 0; k < preds.size(); k++) {
    	  //retrieves criteria question and answer
    	  ServiceQuestion question = preds.get(k).getQuestion();
    	  ServiceAnswer critAnswer = preds.get(k).getAnswer(); 
    	  // goes through each user answer to see if it matches the question
    	  for(ServiceAnswer userAnswer : u.getServiceAnswers()) {
    		  //if it does retrieve the proper answer and compare 
    		  System.out.println(userAnswer.toString());
    		  System.out.println("USER QUESTION " + userAnswer.getServiceQuestion().getId());
    		  System.out.println(question.getId());
    		  if(userAnswer.getServiceQuestion().getId() == question.getId()) {
    			  String type = question.getType();
    			  switch(type) {
    	            case "RANGE":
    	            	System.out.println(userAnswer.getMinRangeAnswer());
    	            	System.out.println(critAnswer.getMinRangeAnswer());
    	            	System.out.println(userAnswer.getMaxRangeAnswer());
    	            	System.out.println(critAnswer.getMaxRangeAnswer());
    	              if (findRange(userAnswer.getMinRangeAnswer(), critAnswer.getMinRangeAnswer(),
    	            		  		userAnswer.getMaxRangeAnswer(), critAnswer.getMaxRangeAnswer())) {
    	                rank++;
    	              }
    	              System.out.println("RANGEANSWER !!!!");
    	              break;
    	            case "TRUE_FALSE":
    	              if (userAnswer.getTrueFalseAnswer() == critAnswer.getTrueFalseAnswer()) {
    	                rank++;
    	              }
    	              break;
    	            case "MULTIPLE_CHOICE":
    	            	
    	            	System.out.println("USER : " + u.getId() + " : ANSWER " + userAnswer.getChoiceAnswer() );
    	            	System.out.println("criteria answer :" + critAnswer.getChoiceAnswer());
    	              if (userAnswer.getChoiceAnswer() == critAnswer.getChoiceAnswer()) {
    	               rank++;
    	               break;
    	              }
    	          }
    		  }
    	  }
      }
      //once all the questions have been checked the user and rank are added
      //if there are no matching criteria the user is not added
      UserToInt addToList = new UserToInt(u, rank);
      if(addToList.getRank() > -1 ) {
    	  resutltsToInt.add(addToList);
      }
    }
      
      /*
      for(int i = 0; i < answers.size(); i++){
    	//Gets each users answers   
        ServiceAnswer s = answers.get(i);
        int minUser = s.getMinRangeAnswer();
        int maxUser = s.getMaxRangeAnswer();
        boolean boolsUser = s.getTrueFalseAnswer();
        int choiceUser = s.getChoiceAnswer();


        for(int k = 0; k < preds.size(); k++) {

          SearchPredicate p = preds.get(i);

          ServiceAnswer critAnsw = p.getAnswer();
          ServiceQuestion critQ = p.getQuestion();

          int minCrit = critAnsw.getMinRangeAnswer();
          int maxCrit = critAnsw.getMaxRangeAnswer();
          boolean boolsCrit = critAnsw.getTrueFalseAnswer();
          int choiceCrit = critAnsw.getChoiceAnswer();

          QuestionType type = critQ.getType();

          switch(type) {

            case RANGE:
              if (findRange(minUser, minCrit, maxUser, maxCrit)) {
                rank++;
              }
              break;
            case TRUE_FALSE:
              if (boolsCrit == boolsUser) {
                rank++;
              }
              break;
            case MULTIPLE_CHOICE:
              if (choiceCrit == choiceUser) {
               rank++;
               break;
              }
          }
        }

      }
      */
      

    //for(UserToInt uI : resutltsToInt) {
    //  if (uI.getRank() == 0) {
    //    resutltsToInt.remove(uI);
    //  }
    //}

    Collections.sort(resutltsToInt, new UserComparator());
    for (int j = 0; j < resutltsToInt.size(); j++) {

      User toAdd = resutltsToInt.get(j).getTheUser();
      result.add(toAdd);
    }


    return result;
  }

  /**
   * Helper method to check if things are in range of each other.
   * @param min1 minimum to check.
   * @param min2 minimum to check against.
   * @param max1 maximum to check.
   * @param max2 max to check against.
   * @return a boolean showing whether the integers are in range.
   */
  private static boolean findRange(int min1, int min2, int max1, int max2) {

    return (min1 <= min2) && (max1 >= max2);

  }




}
