package com.example.cs4500_sp19_noideainc.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents an answer that a service provider would give to a user for anything
 * they might need to know
 */
@Entity
@Table(name="frequently_asked_answers")
public class FrequentlyAskedAnswer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    // A detailed answer to the specific question
    private String answer;

    // The Question being answered
    @ManyToOne
    @JsonIgnore
    private FrequentlyAskedQuestion frequentlyAskedQuestion;
    // The user asking thr question
    @ManyToOne
    @JsonIgnore
    private User user;
    @Transient
    private String question;

    public FrequentlyAskedAnswer() {}

    public FrequentlyAskedAnswer(Integer id, String answer) {
        this.id = id;
        this.answer = answer;
    }

  /**
   * Getter method for question .
   * @return String - the question.
   */
    public String getQuestion() {
        return frequentlyAskedQuestion.getQuestion();
    }
  /**
   * Setter  method for question .
   */
    public void setQuestion(String question) {
        this.question = question;
    }

  /**
   * Getter method for User.
   * @return User - the User.
   */
    public User getUser() {
        return user;
    }

  /**
   * Setter method for User.
   */
    public void setUser(User user) {
        this.user = user;
    }

  /**
   * Getter method for the Question.
   * @return FrequesntlyAskedQuestion - the Question being asked.
   */
    public FrequentlyAskedQuestion getFrequentlyAskedQuestion() {
        return frequentlyAskedQuestion;
    }
  /**
   * Setter method for the Question.
   */
    public void setFrequentlyAskedQuestion(FrequentlyAskedQuestion frequentlyAskedQuestion) {
        this.frequentlyAskedQuestion = frequentlyAskedQuestion;
    }
  /**
   * Getter method for ID.
   * @return Integer - the ID.
   */
    public Integer getId() {
        return id;
    }

  /**
   * Setter method for ID.
   */
    public void setId(Integer id) {
        this.id = id;
    }

  /**
   * Getter method for the text of the answer.
   * @return String - the answer.
   */
    public String getAnswer() {
        return answer;
    }

  /**
   * Setter method for the text of the answer.
   */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}