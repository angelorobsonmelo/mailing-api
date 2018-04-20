package com.angelorobson.mailinglist.services;

import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.ContactRepository;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactServiceTest {

    @MockBean
    ContactRepository contactRepository;

    @InjectMocks
    @Autowired
    private ContactService contactService;

    private Contact contact;
    private ContactFilter contactFilter;
    private static final Long ID = 1L;

    @Before
    public void setUp() {
        contact = new Contact();
        contactFilter = new ContactFilter();
    }

    @Test
    public void it_should_save() {
        when(contactRepository.save(contact)).thenReturn(contact);

        Contact contactReturned = contactService.persist(contact);

        verify(contactRepository).save(eq(contact));
        assertNotNull(contactReturned);
    }

    @Test
    public void it_find_by_filter() {
        when(contactRepository.findAllByFilter(any(ContactFilter.class), any(PageRequest.class))).thenReturn(new PageImpl<>
                (new ArrayList<>()));

        Pageable pageable = new PageRequest(0,10);

        Page<Contact> contactsReturned = contactService.findAllByfilter(contactFilter, pageable);

        verify(contactRepository).findAllByFilter(eq(contactFilter), eq(pageable));
        assertNotNull(contactsReturned);
    }

    @Test
    public void it_should_find_by_id() {
        when(contactRepository.findOne(ID)).thenReturn(contact);

        Optional<Contact> contactReturned = contactService.findById(ID);

        verify(contactRepository).findOne(eq(ID));
        assertNotNull(contactReturned);
    }

    @Test
    public void it_should_find_edit() {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact contactReturned = contactService.edit(contact);

        verify(contactRepository).save(eq(contact));
        assertNotNull(contactReturned);
    }

    @Test
    public void it_should_remove() {
      contactService.remove(ID);

      verify(contactRepository).delete(eq(ID));
    }


}
