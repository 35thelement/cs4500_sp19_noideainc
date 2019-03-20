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
	
}