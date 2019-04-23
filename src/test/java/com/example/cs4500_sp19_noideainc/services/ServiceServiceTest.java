package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.cs4500_sp19_noideainc.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(ServiceService.class)
public class ServiceServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceService serviceService;

    private Service clean = new Service(111, "clean", "House cleaning services");
    private Service repair = new Service(112, "repair", "Repair damaged floor");

    private ServiceCategory petting = new ServiceCategory(2, "Pets");
    private ServiceCategory care = new ServiceCategory(3, "Care");
    private Service petService = new Service(2, "Pet Care", "taking care of your pets");
    private List<ServiceCategory> categories = Arrays.asList(petting, care);

    @Test
    public void testFindServiceById() throws Exception {
        when(serviceService.findServiceById(111)).thenReturn(clean);
        this.mockMvc
                .perform(get("/api/services/111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(111)))
                .andExpect(jsonPath("$.title", is("clean")))
                .andExpect(jsonPath("$.description", is("House cleaning services")));
    }

    @Test
    public void testFindAllServices() throws Exception {
        when(serviceService.findAllService()).thenReturn(Arrays.asList(clean, repair));
        this.mockMvc
                .perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("clean", "repair")));
    }

    @Test
    public void testCreateService() throws Exception {
        ObjectMapper cleanMapper = new ObjectMapper();


        when(serviceService.createService(clean)).thenReturn(repair);
        MvcResult result = this.mockMvc
                .perform(post("/api/services")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(cleanMapper.writeValueAsString(clean)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateService() throws Exception {
        ObjectMapper cleanMapper = new ObjectMapper();

        Service cleaner = new Service(111, "cleaner", "Improved house cleaning services");

        when(serviceService.updateService(clean.getId(), cleaner)).thenReturn(clean);
        this.mockMvc
                .perform(put("/api/services/111")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(cleanMapper.writeValueAsString(cleaner)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateServiceScore() throws Exception {
        Service pet = new Service(2, "Pet Care", "taking care of your pets");
        pet.setScore(5);
        ObjectMapper petMapper = new ObjectMapper();

        when(serviceService.updateServiceScore(petService.getId(), pet)).thenReturn(petService);
          this.mockMvc
                  .perform(put("/api/services/score/2")
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(petMapper.writeValueAsString(pet)))
                  .andExpect(status().isOk())
                  .andDo(print())
                  .andReturn();

        when(serviceService.findServiceById(2)).thenReturn(pet);
          this.mockMvc
                  .perform(get("/api/services/2"))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.id", is(2)))
                  .andExpect(jsonPath("$.score", is(5)));
    }

   @Test
   public void testfindAllCategoriesForService() throws Exception {
        petService.setServiceCategories(categories);

        when(serviceService.findAllCategoriesForService(petService.getId())).thenReturn(categories);
            this.mockMvc
                    .perform(get("/api/services/" + petService.getId() + "/categories"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 3)))
                    .andExpect(jsonPath("$[*].title", containsInAnyOrder("Pets", "Care")));
    }

  }