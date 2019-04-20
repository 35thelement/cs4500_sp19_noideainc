package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import com.example.cs4500_sp19_noideainc.models.UserType;
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

import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;
import com.example.cs4500_sp19_noideainc.models.User;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;
import com.example.cs4500_sp19_noideainc.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAuthenticationService.class)
@AutoConfigureMockMvc
public class UserAuthenticationServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserService userService;
	

	private User nate = new User(123, UserType.Client, "nate", "password", "Nate", "Jones");
	private String nateJSON = "{\"id\":123,\"userType\":\"Client\",\"username\":\"nate\",\"email\":\"nate@gmail.com\",\"password\":\"password\",\"firstName\":\"Nate\",\"lastName\":\"Jones\"}";
	private User sam = new User(234, UserType.Client, "sam", "password1", "Sam", "Smith");
	private User lucy = new User(124, UserType.Client, "lucy", "lucy", "Lucy", "Liu");
	private String lucyJSON = "{\"id\":124,\"userType\":\"Client\",\"username\":\"lucy\",\"email\":\"lucy@gmail.com\",\"password\":\"lucy\",\"firstName\":\"Lucy\",\"lastName\":\"Liu\"}";

    
    @Test
    public void testLogin() throws Exception {
    	nate.setEmail("nate@gmail.com");
        when(userRepository.findByUserEmail("nate@gmail.com")).thenReturn(nate);
        // when log in successfully, it will return the user object
        this.mockMvc
		.perform(post("/api/login/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(nateJSON))
		.andDo(print())
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(123)))
        .andExpect(jsonPath("$.username", is("nate")))
        .andExpect(jsonPath("$.password", is("password")))
        .andExpect(jsonPath("$.email", is("nate@gmail.com")))
        .andExpect(jsonPath("$.firstName", is("Nate")))
        .andExpect(jsonPath("$.lastName", is("Jones")));
    }

		@Test (expected = Exception.class)
    public void testLoginFail1() throws Exception {
    	nate.setEmail("nate@gmail.com");
        when(userRepository.findByUserEmail("nate@gmail.com")).thenReturn(null);
        // when cannot find this user by email, it will throw error
        this.mockMvc
		.perform(post("/api/login/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(nateJSON))
		.andDo(print())
		.andExpect(status().isOk());
    }

		@Test (expected = Exception.class)
    public void testLoginFail2() throws Exception {
    	nate.setEmail("nate@gmail.com");
        when(userRepository.findByUserEmail("nate@gmail.com")).thenReturn(sam);
        // when password does not match the email, it will throw error
        this.mockMvc
		.perform(post("/api/login/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(nateJSON))
		.andDo(print())
		.andExpect(status().isOk());
    }
    
    @Test
    public void testLogout() throws Exception {
    	sam.setEmail("sam@gmail.com");
    	HashMap<String, Object> sessionAttris = new HashMap<String, Object>();
    	// store the session and do invalidate session
    	sessionAttris.put("currentUser", sam);
        this.mockMvc
		.perform(post("/api/logout/").sessionAttrs(sessionAttris))
		.andDo(print())
		.andExpect(status().isOk());
    }
    
    @Test
    public void testcheckLog() throws Exception {
    	sam.setEmail("sam@gmail.com");
    	HashMap<String, Object> sessionAttris = new HashMap<String, Object>();
    	// store the session and check if this service function really check session for login user
    	// if it is, it will return that user object
    	sessionAttris.put("currentUser", sam);
        this.mockMvc
		.perform(get("/api/checkLogin/").sessionAttrs(sessionAttris))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(234)))
        .andExpect(jsonPath("$.username", is("sam")))
        .andExpect(jsonPath("$.password", is("password1")))
        .andExpect(jsonPath("$.email", is("sam@gmail.com")))
        .andExpect(jsonPath("$.firstName", is("Sam")))
        .andExpect(jsonPath("$.lastName", is("Smith")));
    }
 
    
	@Test
	// test the user authentication service for successfully creating a new user
	public void testRegister() throws Exception {
		lucy.setEmail("lucy@gmail.com");
		when(userRepository.findByUserEmail("lucy@gmail.com")).thenReturn(null);
		Mockito.when(userRepository.save(lucy)).thenReturn(lucy);
		this.mockMvc
		.perform(post("/api/register/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(lucyJSON))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test (expected = Exception.class)
	public void testRegisterFail() throws Exception {
		nate.setEmail("nate@gmail.com");
		when(userRepository.findByUserEmail("nate@gmail.com")).thenReturn(nate);
		// when the email is already existed, it will throw error
		this.mockMvc
		.perform(post("/api/register/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(nateJSON))
		.andDo(print())
		.andExpect(status().isOk());
}

}
