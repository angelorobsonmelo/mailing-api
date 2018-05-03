package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.services.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.angelorobson.mailinglist.cors.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @MockBean
 private CorsFilter corsFilter;

  private static final String URL_BASE = "/categories";

  private Page<Category> pagedResponse;

  private static final Long ID = 1L;

  private Category partner;
  private Category cadidate;

  @Before
  public void setUp() {
    partner = new Category();
    cadidate = new Category();

    partner.setCategory("Patner");
    cadidate.setCategory("Cadidate");

    List<Category> categories = asList(partner,cadidate);
    pagedResponse = new PageImpl(categories);
  }

  @Test
  @WithMockUser
  public void it_shold_return_returned() throws Exception {
    given(categoryService.findAll(any(PageRequest.class))).willReturn(pagedResponse);

    mockMvc.perform(get(URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.content[0].category").value(partner.getCategory()))
      .andExpect(jsonPath("$.data.content[1].category").value(cadidate.getCategory()))
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  @Test
  public void it_should_access_denied_when_persist_without_logged() throws Exception {
    mockMvc.perform(delete(URL_BASE + "/" +ID))
            .andExpect(status().isUnauthorized());
  }


  @Test
  @WithMockUser
  public void it_should_persist() throws Exception {
    given(categoryService.persist(any(Category.class))).willReturn(partner);

    String jsonRequisition = getJsonRequisitionPost(ID, partner.getCategory());

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isNotEmpty())
      .andExpect(jsonPath("$.errors").isEmpty());
  }


  @Test
  @WithMockUser
  public void it_should_persist_category_with_category_null_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(ID, null);

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Category can not be empty."));
  }



    @Test
    @WithMockUser
    public void it_should_edit() throws Exception {
        given(categoryService.findById(anyLong())).willReturn(of(partner));
        given(categoryService.edit(any(Category.class))).willReturn(partner);

        String jsonRequisition = this.getJsonRequisitionPost(1L, partner.getCategory());

        mockMvc.perform(put(URL_BASE + "/"  + ID)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.category").value("Patner"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }


    @Test
    @WithMockUser
    public void it_should_return_user_not_found_when_editing_a_non_existent_category() throws Exception {
        given(categoryService.findById(anyLong())).willReturn(empty());

        String jsonRequisition = this.getJsonRequisitionPost(1L, partner.getCategory());

        mockMvc.perform(put(URL_BASE + "/" + ID)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors[0]").value("Category not found."));
    }

    @Test
    @WithMockUser
    public void it_should_remove_category() throws Exception {
      given(this.categoryService.findById(anyLong())).willReturn(of(new Category()));

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void it_should_remove_non_existent_category() throws Exception {
      given(this.categoryService.findById(anyLong())).willReturn(empty());

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Error removing category. Record not found for id " + ID));
    }

    @Test
    public void it_should_access_denied_when_removing_a_category_without_logged() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isUnauthorized());
    }

 private String getJsonRequisitionPost(Long id, String category) throws JsonProcessingException {
    Category categoryObj = new Category();
    categoryObj.setId(id);
    categoryObj.setCategory(category);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper.writeValueAsString(categoryObj);
  }

}
