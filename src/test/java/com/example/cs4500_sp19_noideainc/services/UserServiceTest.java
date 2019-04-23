package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cs4500_sp19_noideainc.models.*;
import com.example.cs4500_sp19_noideainc.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private PaymentMethodRepository paymentMethodRepository;
    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private ServiceAnswerRepository serviceAnswerRepository;

    private User bob = new User(456, UserType.Client, "bob", "1234", "Bob", "Wonderland");
    private User nate = new User(128, UserType.Client, "nate", "password", "Nate", "Jones");
    private User alice = new User(123, UserType.Client, "alice", "alice", "Alice", "Wonderland");
    private String nateJSON = "{\"id\":128,\"userType\":\"Client\",\"username\":\"nate\",\"password\":\"password\",\"firstName\":\"Nate\",\"lastName\":\"Jones\"}";
    private User sam = new User(234, UserType.Client, "sam", "password", "Sam", "Smith");
    private Service service = new Service(1, "landscaping", "making your yard look fancy");
    private User business = new User(999, UserType.Provider, "uname", "pass", "F", "L", "E", "BN", 1983, 201, "BusinessEmail", "FB", "IG", "TWTR", 48, true, "1/2/1995", new ArrayList());
    
    @Test
    public void testFindUserById() throws Exception {
        when(userRepository.findUserById(128)).thenReturn(nate);
        this.mockMvc
                .perform(get("/api/users/128"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(128)))
                .andExpect(jsonPath("$.username", is("nate")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.firstName", is("Nate")))
                .andExpect(jsonPath("$.lastName", is("Jones")));
    }

    @Test
    public void testFindAllUsers() throws Exception {
        when(userRepository.findAllUsers()).thenReturn(Arrays.asList(nate, sam));
        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("nate", "sam")));
    }

    @Test
    public void testCreateUser() throws Exception {

        when(userRepository.save(nate)).thenReturn(sam);
        this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nateJSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateUser() throws Exception {
        User theCoolerNate = new User(128, UserType.Client, "cooler_nate", "passwd", "Nathan", "Johnson");
        theCoolerNate.setEmail("nate@gmail.com");
        Review review = new Review(1, "title", "review", 4, theCoolerNate, theCoolerNate, "Thank you", "Nate");
        theCoolerNate.setReviewsOfMe(Arrays.asList(review));
        theCoolerNate.setMyReviewsOfOthers(Arrays.asList(review));
        String coolJSON = "{" +
                "\"id\":128," +
                "\"userType\":\"Client\"," +
                "\"username\":\"cooler_nate\"," +
                "\"password\":\"passwd\"," +
                "\"firstName\":\"Nathan\"," +
                "\"lastName\":\"Johnson\"," +
                "\"email\":\"nate@gmail.com\"," +
                "\"reviewsOfMe\": [{\"id\": 1, \"title\": \"title\", \"rating\": 4, \"reviewerName\": \"Nate\", \"reply\": \"Thank you\"}]," +
                "\"myReviewsOfOthers\": [{\"id\": 1, \"title\": \"title\", \"rating\": 4, \"reviewerName\": \"Nate\", \"reply\": \"Thank you\"}]," +
                "\"hires\": 4," +
                "\"backgroundChecked\": true," +
                "\"paymentMethods\": []," +
                "\"birthday\": \"9/13/1995\"}";

        //User theCoolerNate = new User(123, UserType.Client, "cooler_nate", "passwd", "Nathan", "Johnson");

        when(userRepository.save(nate)).thenReturn(theCoolerNate);
        when(userRepository.findUserById(128)).thenReturn(theCoolerNate);
        this.mockMvc
                .perform(put("/api/users/128")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(coolJSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userRepository).deleteById(128);
        this.mockMvc
                .perform(delete("/api/users/128")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindAddressByUserId() throws Exception {
    	Address address = new Address();

        address.setState("MA");
        address.setCity("Boston");
        address.setZip("02115");
        address.setId(157);
        address.setStreet("108 Huntington Ave");
        address.setResident(alice);
        
        when(addressRepository.findAddressByUserId(123)).thenReturn(address);
        this.mockMvc
                .perform(get("/api/user_address/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(157)))
                .andExpect(jsonPath("$.state", is("MA")))
                .andExpect(jsonPath("$.city", is("Boston")))
                .andExpect(jsonPath("$.zip", is("02115")))
                .andExpect(jsonPath("$.street", is("108 Huntington Ave")));
    }
    
    @Test
    public void testUpdateProfile() throws Exception {
    	User newBob = new User(456, UserType.Provider, "bobby", "1234", "Bobby", "Wonder");
    	Address homeAddress = new Address();
    	
    	homeAddress.setState("NY");
    	homeAddress.setCity("New Jersey");
    	homeAddress.setZip("02005");
    	homeAddress.setId(167);
    	homeAddress.setStreet("Jersey Street");
    	homeAddress.setResident(newBob);
    	Address businessAddress = new Address();
    	
    	businessAddress.setState("MA");
    	businessAddress.setCity("Boston");
    	businessAddress.setZip("02117");
    	businessAddress.setId(168);
    	businessAddress.setStreet("Washington Street");
    	businessAddress.setResident(newBob);
        
    	List<Address> addresses = Arrays.asList(homeAddress, businessAddress);

    	newBob.setBirthday("3/12/1996");
    	newBob.setAddresses(addresses);

    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String jsonAddress = ow.writeValueAsString(addresses);
    	
    	String bobJSON = "{" +
                "\"id\":456," +
                "\"userType\":\"Provider\"," +
                "\"username\":\"bobby\"," +
                "\"password\":\"1234\"," +
                "\"firstName\":\"Bobby\"," +
                "\"lastName\":\"Wonder\"," +
                "\"birthday\": \"3/12/1996\"," +
                "\"addresses\":" + jsonAddress + "}";
    	
    	when(userRepository.save(bob)).thenReturn(newBob);
    	when(userRepository.findUserById(456)).thenReturn(newBob);
    	this.mockMvc
	        .perform(put("/api/profile/456")
	        		.contentType(MediaType.APPLICATION_JSON_UTF8)
	        		.content(bobJSON))
	        .andExpect(status().isOk())
	        .andDo(print())
	        .andReturn();
    	
    	when(userRepository.findUserById(456)).thenReturn(newBob);
        this.mockMvc
                .perform(get("/api/users/456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(456)))
                .andExpect(jsonPath("$.birthday", is("3/12/1996")))
                .andExpect(jsonPath("$.addresses", hasSize(2)));
    }
    
}
