package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="frequently_asked_questions")
public class FrequentlyAskedQuestion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String question;
    @OneToMany(mappedBy="frequentlyAskedQuestion")
    private List<FrequentlyAskedAnswer> answers;

    // default constructor for the FrequentlyAskedQuestion class
    public FrequentlyAskedQuestion() {

    }

    // the constructor for the FrequentlyAskedQuestion class
    public FrequentlyAskedQuestion(Integer id, String title, String question, List<FrequentlyAskedAnswer> answers) {
		this.id = id;
		this.title = title;
		this.question = question;
		this.answers = answers;
	}
    
    public List<FrequentlyAskedAnswer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<FrequentlyAskedAnswer> answers) {
        this.answers = answers;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
}