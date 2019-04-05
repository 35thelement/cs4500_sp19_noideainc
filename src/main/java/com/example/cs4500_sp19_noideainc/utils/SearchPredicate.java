package com.example.cs4500_sp19_noideainc.utils;

import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;

/**
 * This class represents predicates that users can use as part of critria to search
 * for whatever services they would like.
 */
public class SearchPredicate {

  private ServiceQuestion question;
  private ServiceAnswer answer;

  /**
   * This is a constructor that one can use to initialize searchPredicate to whatever
   * you'd like.
   * @param question a service question.
   * @param answer a service answer.
   */
  public SearchPredicate(ServiceQuestion question, ServiceAnswer answer) {
    this.question = question;
    this.answer = answer;
  }

  /**
   * Gets the service question field for this class.
   * @return a service question.
   */
  public ServiceQuestion getQuestion() {
    return question;
  }
  /**
   * Sets the service question field for this class.
   */
  public void setQuestion(ServiceQuestion question) {
    this.question = question;
  }
  /**
   *
   * Gets the service question field for this class.
   * @return a service answer.
   */
  public ServiceAnswer getAnswer() {
    return answer;
  }

  /**
   * Sets the service question field for this class.
   * @return a service answer.
   */
  public void setAnswer(ServiceAnswer answer) {
    this.answer = answer;
  }
  
  public String toString() {
	  System.out.println(question.toString());
	  return "Question: " + question.toString() + " Answer: " + answer.toString();
  }
}