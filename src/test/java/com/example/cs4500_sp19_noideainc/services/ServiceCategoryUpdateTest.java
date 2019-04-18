package com.example.cs4500_sp19_noideainc.services;

import com.example.cs4500_sp19_noideainc.models.ServiceCategory;
import com.example.cs4500_sp19_noideainc.repositories.PagedServiceCategoryRepository;
import com.example.cs4500_sp19_noideainc.repositories.ServiceCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceCategoryService.class)
@AutoConfigureMockMvc
public class ServiceCategoryUpdateTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ServiceCategoryRepository serviceCategoryRepository;
  @MockBean
  private PagedServiceCategoryRepository pagedServiceCategoryRepository;

  // write test for updateServiceCategoryScore method in the ServiceCategoryService
  @Test
  public void testUpdateScoreForServiceCategory() throws Exception {

    ObjectMapper Mapper = new ObjectMapper();
    ServiceCategory updateTest = new ServiceCategory(5, "test update");
    updateTest.setScore(11);
    Mockito.when(serviceCategoryRepository.findServiceCategoryById(5)).thenReturn(updateTest);
    Mockito.when(serviceCategoryRepository.save(updateTest)).thenReturn(updateTest);
    this.mockMvc
            .perform(put("/api/categories/score/5")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(Mapper.writeValueAsString(updateTest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(5)))
            .andExpect(jsonPath("$.title", is("test update")))
            .andExpect(jsonPath("$.score", is(11)))
            .andReturn();
  }

}