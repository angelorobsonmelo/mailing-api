package com.angelorobson.mailinglist.controllers;


import com.angelorobson.mailinglist.dtos.ContactSaveDto;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import com.angelorobson.mailinglist.services.ContactService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramJohn;
import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramRobert;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    private Page<Contact> pagedResponse;

    private static final String URL_BASE = "/contacts";
    private static final Long ID = 1L;

    private Contact john;
    private Contact robert;

    @Before
    public void setUp() {
        john = oneContactWithUserNameInstagramJohn().build();
        robert = oneContactWithUserNameInstagramRobert().build();
        List<Contact> contacts = asList(john, robert);
        pagedResponse = new PageImpl(contacts);
    }

    @Test
    @WithMockUser
    public void it_should_return_contacts_by_filter() throws Exception {
        given(contactService.findAllByfilter(any(ContactFilter.class), any(Pageable.class)))
                .willReturn(pagedResponse);

        String contactFilterJson = getJsonContactFilter();

        mockMvc.perform(post(URL_BASE + "/filter")
                .content(contactFilterJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].userNameInstagram").value(john.getUserNameInstagram()))
                .andExpect(jsonPath("$.data.content[1].userNameInstagram").value(robert.getUserNameInstagram()))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void it_should_persist() throws Exception {
        given(contactService.persist(any(Contact.class))).willReturn(john);

        mockMvc.perform(post(URL_BASE)
                .content(getJsonContactSaveDto(false))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void it_should_edit() throws Exception {
        given(contactService.findById(anyLong())).willReturn(of(john));
        given(contactService.edit(any(Contact.class))).willReturn(john);

        mockMvc.perform(put(URL_BASE + "/" + ID)
                .content(getJsonContactSaveDto(true))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void it_should_remove() throws Exception {
        given(this.contactService.findById(anyLong())).willReturn(of(new Contact()));

        mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isOk());
    }

    private String getJsonContactFilter() throws JsonProcessingException {
        Contact contact = oneContactWithUserNameInstagramJohn().build();

        ContactFilter contactFilter = new ContactFilter();
        contactFilter.setFunctions(contact.getFunctions());
        contactFilter.setCategory(contact.getCategory());
        contactFilter.setGender(contact.getGender());
        contactFilter.setUserNameInstagram(contact.getUserNameInstagram());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        return mapper.writeValueAsString(contactFilter);
    }

    private String getJsonContactSaveDto(Boolean isEditing) throws JsonProcessingException {
        Contact contact = oneContactWithUserNameInstagramJohn().build();
        List<Long> functions = contact.getFunctions().stream().map(Function::getId).collect(Collectors.toList());

        ContactSaveDto contactSaveDto = new ContactSaveDto();

        if (isEditing) {
            contactSaveDto.setId(Optional.of(contact.getId()));
        }

        contactSaveDto.setCategoryId(contact.getCategory().getId());
        contactSaveDto.setGender(contact.getGender().toString());
        contactSaveDto.setUserAppId(contact.getUserApp().getId());
        contactSaveDto.setFunctionsIds(functions);
        contactSaveDto.setUserNameInstagram(contact.getUserNameInstagram());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        return mapper.writeValueAsString(contactSaveDto);
    }

}
