package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.services.FunctionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FunctionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FunctionService functionService;

  private static final String URL_BASE = "/functions";

  private Page<Function> pagedResponse;

  private static final Long ID = 1L;

  private Function storyModel = new Function();
  private Function model = new Function();

  @Before
  public void setUp() {
    storyModel.setFunction("Story Model");
    model.setFunction("Model");

    List<Function> categories =asList(storyModel, model);
    pagedResponse = new PageImpl(categories);
  }

  @Test
  @WithMockUser
  public void it_shold_return_returned() throws Exception {
    given(functionService.findAll(any(PageRequest.class))).willReturn(pagedResponse);
    
    mockMvc.perform(get(URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.content[0].function").value(storyModel.getFunction()))
      .andExpect(jsonPath("$.data.content[1].function").value(model.getFunction()))
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
    given(functionService.persist(any(Function.class))).willReturn(model);

    String jsonRequisition = getJsonRequisitionPost(ID, model.getFunction());

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
        given(functionService.findById(anyLong())).willReturn(of(model));
        given(functionService.edit(any(Function.class))).willReturn(model);

        String jsonRequisition = this.getJsonRequisitionPost(1L, model.getFunction());

        mockMvc.perform(put(URL_BASE + "/"  + ID)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.function").value(model.getFunction()))
                .andExpect(jsonPath("$.errors").isEmpty());
    }


    @Test
    @WithMockUser
    public void it_should_return_user_not_found_when_editing_a_non_existent_category() throws Exception {
        given(functionService.findById(anyLong())).willReturn(empty());

        String jsonRequisition = this.getJsonRequisitionPost(1L, model.getFunction());

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
      given(this.functionService.findById(anyLong())).willReturn(of(new Function()));

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void it_should_remove_non_existent_category() throws Exception {
      given(this.functionService.findById(anyLong())).willReturn(empty());

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Error removing category. Record not found for id " + ID));
    }

    @Test
    public void it_should_access_denied_when_removing_a_category_without_logged() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isUnauthorized());
    }

 private String getJsonRequisitionPost(Long id, String function) throws JsonProcessingException {
    Function categoryObj = new Function();
    categoryObj.setId(id);
    categoryObj.setFunction(function);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper.writeValueAsString(categoryObj);
  }

}
