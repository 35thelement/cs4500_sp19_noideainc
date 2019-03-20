package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FAQRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(FAQService.class)
@AutoConfigureMockMvc
public class FAQServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FAQRepository fAQRepository;

	private FrequentlyAskedQuestion faq1 = new FrequentlyAskedQuestion(1, "background check",
					"Have you passed a background check?", null);
	private FrequentlyAskedQuestion faq2 = new FrequentlyAskedQuestion(2, "number of employees",
					"How many employees does your company have?", null);
	private FrequentlyAskedQuestion faq3 = new FrequentlyAskedQuestion(3, "company's age",
					"Have you passed a background check?", null);

	@Test
	// test the web services for the find all FAQs
	public void testFindAllFAQ() throws Exception {
		List<FrequentlyAskedQuestion> faqList = Arrays.asList(faq1, faq2, faq3);
		Mockito.when(fAQRepository.findAllFrequentlyAskedQuestions()).thenReturn(faqList);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/api/faqs"))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$", hasSize(3)))
		    .andExpect(jsonPath("$[*].title", containsInAnyOrder("background check", "number of employees",
		    		"company's age")))
		    .andExpect(jsonPath("$[*].question", containsInAnyOrder("Have you passed a background check?",
		    		"How many employees does your company have?", "Have you passed a background check?")))
		    .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2, 3)));
	}
	
	@Test
	// test the web services for the find FAQs by faqId
	public void testFindFAQById() throws Exception {

		Mockito.when(fAQRepository.findFrequentlyAskedQuestionById(1)).thenReturn(faq1);
		this.mockMvc
			.perform(get("/api/faqs/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is("background check")))
			.andExpect(jsonPath("$.question", is("Have you passed a background check?")))
			.andExpect(jsonPath("$.id", is(1)));

	}
	
	@Test
	// test the web services for the create a new FAQs
	public void testCreateFAQ() throws Exception {
		ObjectMapper Mapper = new ObjectMapper();
		// new faq does not have ID
		FrequentlyAskedQuestion newFaq = new FrequentlyAskedQuestion(1, "company's age",
						"Have you passed a background check?", null);
		String jsonString = Mapper.writeValueAsString(newFaq);
		System.out.println("here!!!");
		System.out.println(jsonString);
		when(fAQRepository.existsById(1)).thenReturn(false);
		Mockito.when(fAQRepository.save(newFaq)).thenReturn(newFaq);
		this.mockMvc
			.perform(post("/api/faqs/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON)
					.content(jsonString))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	
	@Test
	// test the web services for the update the FAQs
	public void testUpdateFAQ() throws Exception {
		ObjectMapper Mapper = new ObjectMapper();
		FrequentlyAskedQuestion updateFaq2 = new FrequentlyAskedQuestion(2,
						"new title for the faq2", "How many employees does your company have?", null);
		faq2.setTitle("new title for the faq2");
		Mockito.when(fAQRepository.save(faq2)).thenReturn(updateFaq2);
		Mockito.when(fAQRepository.findFrequentlyAskedQuestionById(2)).thenReturn(updateFaq2);
		this.mockMvc
			.perform(put("/api/faqs/2")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(Mapper.writeValueAsString(faq2)))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	// test the web services for the delete the FAQs
	public void testDeleteFAQ() throws Exception {
		doNothing().when(fAQRepository).deleteById(3);
		this.mockMvc
			.perform(delete("/api/faqs/3")
					.accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(print());
	}

}