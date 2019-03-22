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

    private User nate = new User(123, "nate", "password", "Nate", "Jones");
    private User sam = new User(234, "sam", "password", "Sam", "Smith");
    private Service service = new Service(1, "landscaping", "making your yard look fancy");

    @Test
    public void testFindUserById() throws Exception {
        when(userRepository.findUserById(123)).thenReturn(nate);
        this.mockMvc
                .perform(get("/api/users/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
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
        ObjectMapper nateMapper = new ObjectMapper();


        when(userRepository.save(nate)).thenReturn(sam);
        this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nateMapper.writeValueAsString(nate)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateUser() throws Exception {
        ObjectMapper nateMapper = new ObjectMapper();

        User theCoolerNate = new User(123, "cooler_nate", "passwd", "Nathan", "Johnson");

        when(userRepository.save(nate)).thenReturn(theCoolerNate);
        when(userRepository.findUserById(123)).thenReturn(theCoolerNate);
        this.mockMvc
                .perform(put("/api/users/123")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nateMapper.writeValueAsString(nate)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userRepository).deleteById(123);
        this.mockMvc
                .perform(delete("/api/users/123")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
