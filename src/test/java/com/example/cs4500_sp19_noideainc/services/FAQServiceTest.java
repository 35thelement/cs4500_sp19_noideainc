package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.repositories.FAQRepository;
import com.example.cs4500_sp19_noideainc.repositories.PagedFAQRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(FAQService.class)
@AutoConfigureMockMvc
public class FAQServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FAQRepository fAQRepository;
	@MockBean
	private PagedFAQRepository pagedFaqRepository;

	private FrequentlyAskedQuestion faq1 = new FrequentlyAskedQuestion(1, "background check",
					"Have you passed a background check?", null);
	private FrequentlyAskedQuestion faq2 = new FrequentlyAskedQuestion(2, "number of employees",
					"How many employees does your company have?", null);
	private FrequentlyAskedQuestion faq3 = new FrequentlyAskedQuestion(3, "company's age",
					"Have you passed a background check?", null);
	private FrequentlyAskedQuestion faq4 = new FrequentlyAskedQuestion(4, "promotional offers",
			"Are there any promotional offers?", null);
	private FrequentlyAskedQuestion faq5 = new FrequentlyAskedQuestion(5, "selling points",
			"What sets you apart from other service providers?", null);
	private FrequentlyAskedQuestion faq6 = new FrequentlyAskedQuestion(6, "insurance",
			"What kind of insurance do you have?", null);
	private FrequentlyAskedQuestion faq7 = new FrequentlyAskedQuestion(7, "client",
			"Are your end client happy?", null);
	private FrequentlyAskedQuestion faq8 = new FrequentlyAskedQuestion(8, "specialization",
			"What is your specialization?", null);
	private FrequentlyAskedQuestion faq9 = new FrequentlyAskedQuestion(9, "free swags",
			"Are there free swags?", null);
	private FrequentlyAskedQuestion faq10 = new FrequentlyAskedQuestion(10, "location",
			"Where are you located?", null);
	private FrequentlyAskedQuestion faq11 = new FrequentlyAskedQuestion(11, "security check",
			"Have you passed a security check?", null);
	private FrequentlyAskedQuestion faq12 = new FrequentlyAskedQuestion(12, "food check",
			"Have you passed a food check?", null);

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
	// test the web services for the filtering FAQs
	public void testfilterFAQs() throws Exception {
		List<FrequentlyAskedQuestion> faqList = Arrays.asList(faq1, faq11, faq12);
		Mockito.when(fAQRepository.filterFAQs("check", "have")).thenReturn(faqList);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/api/faqs/filtered/?title=check&question=have"))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$", hasSize(3)))
		    .andExpect(jsonPath("$[*].title", containsInAnyOrder("background check", "security check", "food check")))
		    .andExpect(jsonPath("$[*].question", containsInAnyOrder("Have you passed a background check?",
		    		"Have you passed a security check?", "Have you passed a food check?")))
		    .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 11, 12)));
	}
	
	@Test
	// test the web services for the filtering FAQs
	public void testfilterFAQsReturnAll() throws Exception {
		List<FrequentlyAskedQuestion> faqList = Arrays.asList(faq1, faq2, faq11, faq12);
		Mockito.when(fAQRepository.findAllFrequentlyAskedQuestions()).thenReturn(faqList);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/api/faqs/filtered"))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$", hasSize(4)))
		    .andExpect(jsonPath("$[*].title", containsInAnyOrder("background check", "security check", "food check", "number of employees")))
		    .andExpect(jsonPath("$[*].question", containsInAnyOrder("Have you passed a background check?", "How many employees does your company have?", 
		    		"Have you passed a security check?", "Have you passed a food check?")))
		    .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2, 11, 12)));
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
	
	@Test
	// test returning the correct paged FAQs with the count of 10 and page number of 0
	public void testFindPagedTenFAQs() throws Exception {
		List<FrequentlyAskedQuestion> faqList = Arrays.asList(faq1, faq2, faq3, faq4, faq5, faq6, faq7,
				faq8, faq9, faq10);
		Pageable p = PageRequest.of(0, 10);
		Page<FrequentlyAskedQuestion> expected = new PageImpl<FrequentlyAskedQuestion>(faqList);

		Mockito.when(pagedFaqRepository.findAll(p)).thenReturn(expected);
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/api/faqs/paged"))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		    .andExpect(jsonPath("$.content", hasSize(10)))
		    .andExpect(jsonPath("$.content[*].title", containsInAnyOrder("background check", 
		    		"number of employees", "company's age", "promotional offers", "selling points", 
		    		"insurance", "client", "specialization", "free swags", "location")))
		    .andExpect(jsonPath("$.content[*].question", containsInAnyOrder("Have you passed a background check?",
		    		"How many employees does your company have?", "Have you passed a background check?",
		    		"Are there any promotional offers?", "What sets you apart from other service providers?",
		    		"What kind of insurance do you have?", "Are your end client happy?", 
		    		"What is your specialization?", "Are there free swags?", "Where are you located?")))
		    .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
	}
}