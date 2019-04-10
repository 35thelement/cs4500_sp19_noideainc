package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.example.cs4500_sp19_noideainc.models.*;
import com.example.cs4500_sp19_noideainc.repositories.ServiceRepository;
import com.example.cs4500_sp19_noideainc.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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


@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ServiceRepository serviceRepository;

    private User nate = new User(128, UserType.Client, "nate", "password", "Nate", "Jones");
    private String nateJSON = "{\"id\":128,\"userType\":\"Client\",\"username\":\"nate\",\"password\":\"password\",\"firstName\":\"Nate\",\"lastName\":\"Jones\"}";
    private User sam = new User(234, UserType.Client, "sam", "password", "Sam", "Smith");
    private Service service = new Service(1, "landscaping", "making your yard look fancy");

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
        Review review = new Review(1, "title", "review", 4, theCoolerNate, theCoolerNate);
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
                "\"reviewsOfMe\": [{\"id\": 1, \"title\": \"title\", \"rating\": 4}]," +
                "\"myReviewsOfOthers\": [{\"id\": 1, \"title\": \"title\", \"rating\": 4}]}";

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
}
