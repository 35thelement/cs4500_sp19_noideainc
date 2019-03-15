package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.example.cs4500_sp19_noideainc.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    private User nate = new User(123, "nate", "password", "Nate", "Jones");
    private User sam = new User(234, "sam", "password", "Sam", "Smith");

    @Test
    public void testFindUserById() throws Exception {
        when(userService.findUserById(123)).thenReturn(nate);
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
        when(userService.findAllUser()).thenReturn(Arrays.asList(nate, sam));
        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("nate", "sam")));
    }

    @Test
    public void testCreateUser() throws Exception {
        ObjectMapper nateMapper = new ObjectMapper();


        when(userService.createUser(nate)).thenReturn(sam);
        MvcResult result = this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nateMapper.writeValueAsString(nate)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateUser() throws Exception {
        ObjectMapper nateMapper = new ObjectMapper();

        User theCoolerNate = new User(123, "cooler_nate", "password", "Nate", "Jones");

        when(userService.updateUser(nate.getId(), theCoolerNate)).thenReturn(nate);
        this.mockMvc
                .perform(put("/api/users/123")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(nateMapper.writeValueAsString(theCoolerNate)))
                .andExpect(status().isOk());
    }
}
