package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.dtos.UserAppEditDto;
import com.angelorobson.mailinglist.dtos.UserAppSaveDto;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.services.UserAppService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.angelorobson.mailinglist.builders.UserAppBuilder.oneUserWithNameJoao;
import static com.angelorobson.mailinglist.builders.UserAppDtoBuilder.oneUserDotWithNameManoel;
import static com.angelorobson.mailinglist.builders.UserAppDtoBuilder.oneUserDtoWithNameJoao;
import static java.util.Arrays.asList;
import static java.util.Optional.*;
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
public class UserAppControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAppService userAppService;

  private static final String URL_BASE = "/users_app";

  private Page<UserAppDto> pagedResponse;

  private static final Long ID = 1L;

  private UserAppDto manoel;
  private UserAppDto joao;

  @Before
  public void setUp() {
    manoel = oneUserDotWithNameManoel().build();
    joao = oneUserDtoWithNameJoao().build();
    List<UserAppDto> userAppsDto = asList(manoel,joao);
    pagedResponse = new PageImpl(userAppsDto);
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_shold_return_users() throws Exception {
    given(userAppService.findAll(any(PageRequest.class))).willReturn(pagedResponse);
    
    mockMvc.perform(get(URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.content[0].firstName").value(manoel.getFirstName()))
      .andExpect(jsonPath("$.data.content[0].lastName").value(manoel.getLastName()))
      .andExpect(jsonPath("$.data.content[0].email").value(manoel.getEmail()))
            .andExpect(jsonPath("$.data.content[1].firstName").value(joao.getFirstName()))
      .andExpect(jsonPath("$.data.content[1].lastName").value(joao.getLastName()))
      .andExpect(jsonPath("$.data.content[1].email").value(joao.getEmail()))
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  @Test
  public void it_should_access_denied_when_persist_a_user_without_logged() throws Exception {
    mockMvc.perform(delete(URL_BASE + "/" +ID))
            .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(manoel);

    String jsonRequisition = getJsonRequisitionPost(null, manoel.getFirstName(), manoel.getLastName(), manoel.getEmail(),
            "123");

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isNotEmpty())
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_firstname_null_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(null,null, "silva", "jose@gmai.com",
            "123");

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Firstname can not be empty."));
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_lastname_null_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(null,"José", null, "jose@gmai.com",
            "123");

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Lastname can not be empty."));
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_email_null_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "Silva", null,
            "123");

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Email can not be empty."));
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_invalid_email_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "Silva", "emailinvalido",
            "123");

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Invalid email."));
  }

  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_password_null_value() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "Silva", "admin@gmail.com",
            null);

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Password can not be empty."));
  }


  @Test
  @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
  public void it_should_persist_user_with_all_empty_required_fields() throws Exception {
    String jsonRequisition = this.getJsonRequisitionPost(empty(), null, null,
            null, null);

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").isNotEmpty())
      .andExpect(jsonPath("$.errors[1]").isNotEmpty())
      .andExpect(jsonPath("$.errors[2]").isNotEmpty())
      .andExpect(jsonPath("$.errors[3]").isNotEmpty());
  }

    @Test
    @WithMockUser
    public void it_should_edit_user() throws Exception {
        given(userAppService.findById(anyLong())).willReturn(of(oneUserWithNameJoao().build()));
        given(userAppService.edit(any(UserApp.class))).willReturn(joao);

        String jsonRequisition = this.getJsonRequisitionPut(of(1L), joao.getFirstName(), joao.getLastName(),
                joao.getEmail(), "123");

        mockMvc.perform(put(URL_BASE + "/"  + ID)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("João"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }


    @Test
    @WithMockUser
    public void it_should_return_user_not_found_when_editing_a_non_existent_user() throws Exception {
        given(userAppService.findById(anyLong())).willReturn(empty());
        given(userAppService.edit(any(UserApp.class))).willReturn(manoel);

      String jsonRequisition = this.getJsonRequisitionPut(of(1L), joao.getFirstName(), joao.getLastName(),
              joao.getEmail(), "123");

        mockMvc.perform(put(URL_BASE + "/" + ID)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors[0]").value("User not found."));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void it_should_remove_user() throws Exception {
      given(this.userAppService.findById(anyLong())).willReturn(of(new UserApp()));

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void it_should_remove_non_existent_user() throws Exception {
      given(this.userAppService.findById(anyLong())).willReturn(empty());

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Error removing user. Record not found for id " + ID));
    }

    @Test
    @WithMockUser
    public void it_should_access_denied_when_removing_a_user() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isForbidden());
    }

    @Test
    public void it_should_access_denied_when_removing_a_user_without_logged() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isUnauthorized());
    }

 private String getJsonRequisitionPost(Optional<Long> id, String firstName, String lastName, String email, String password) throws JsonProcessingException {
    UserAppSaveDto userAppSaveDto = new UserAppSaveDto();
    userAppSaveDto.setId(id);
    userAppSaveDto.setFirstName(firstName);
    userAppSaveDto.setLastName(lastName);
    userAppSaveDto.setEmail(email);
    userAppSaveDto.setPassword(password);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper.writeValueAsString(userAppSaveDto);
  }


  private String getJsonRequisitionPut(Optional<Long> id, String firstName, String lastName, String email, String password) throws JsonProcessingException {
    UserAppEditDto userAppEditDto = new UserAppEditDto();
    userAppEditDto.setId(id);
    userAppEditDto.setFirstName(firstName);
    userAppEditDto.setLastName(lastName);
    userAppEditDto.setPassword(password);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper.writeValueAsString(userAppEditDto);
  }

}
