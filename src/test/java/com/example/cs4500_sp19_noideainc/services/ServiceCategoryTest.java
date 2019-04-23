package com.example.cs4500_sp19_noideainc.services;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.repositories.ServiceCategoryRepository;
import com.example.cs4500_sp19_noideainc.repositories.PagedServiceCategoryRepository;
import com.example.cs4500_sp19_noideainc.services.ServiceCategoryService;


@RunWith(SpringRunner.class)
@WebMvcTest(ServiceCategoryService.class)
public class ServiceCategoryTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceCategoryService service;
    @MockBean
    private ServiceCategoryRepository ServiceCategoryRepository;
    //@MockBean
    //private PagedServiceCategoryRepository pagedRepository;

    private ServiceCategory tutoring = new ServiceCategory(1, "Tutoring");
    private ServiceCategory plumbing = new ServiceCategory(2, "Plumbing");
    private List<ServiceCategory> categories = Arrays.asList(tutoring, plumbing);
    
    private ServiceCategory petting = new ServiceCategory(3, "Pets");
    private ServiceCategory care = new ServiceCategory(3, "Care");
    private List<ServiceCategory> petCategory = Arrays.asList(petting);
    private List<ServiceCategory> careCategory = Arrays.asList(care, petting);
    private Service pet1 = new Service(1, "Dog Walking", "in charge your dog");
    private Service pet2 = new Service(2, "Pet Care", "taking care of your pets");
    private Service care1 = new Service(3, "Hair care", "taking care of your hair");
    private List<Service> petsServices = Arrays.asList(pet1, pet2);
    private List<Service> careServices = Arrays.asList(pet2, care1);


    @Test
    public void testFindAllServiceCategories() throws Exception {
        when(service.findAllServiceCategories()).thenReturn(categories);
        this.mockMvc
                .perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("Tutoring", "Plumbing")));
    }

    @Test
    public void testFindServiceCategoryById() throws Exception {
        when(service.findServiceCategoryById(1)).thenReturn(tutoring);
        when(service.findServiceCategoryById(2)).thenReturn(plumbing);
        this.mockMvc
                .perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Tutoring")));
        this.mockMvc
                .perform(get("/api/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Plumbing")));
    }

    @Test
    public void testUpdateServiceCategory() throws Exception {
        ObjectMapper scMapper = new ObjectMapper();
        ServiceCategory newSC = new ServiceCategory(1, "New Tutoring");

        when(service.updateServiceCategory(tutoring.getId(), newSC)).thenReturn(newSC);
        this.mockMvc
                .perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scMapper.writeValueAsString(newSC)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateServiceCategory() throws Exception {
        ObjectMapper scMapper = new ObjectMapper();
        ServiceCategory tutorTest = new ServiceCategory();
        tutoring.setTitle("Tutoring");

        when(service.createServiceCategory(tutorTest)).thenReturn(tutoring);
        this.mockMvc
                .perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scMapper.writeValueAsString(tutorTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteServiceCategory() throws Exception {
        this.mockMvc
                .perform(delete("/api/categories/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testFindAllServicesByCategoryName() throws Exception {
    	pet1.setServiceCategories(petCategory);
    	pet2.setServiceCategories(petCategory);
        when(service.findAllServicesByCategoryName("Pets")).thenReturn(petsServices);
        this.mockMvc
                .perform(get("/api/Pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("Dog Walking", "Pet Care")))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder("in charge your dog", 
                		"taking care of your pets")));
    }

}
