package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactRepositoryTest extends ContactRepositoryBaseTest {


    @Test
    public void it_should_return_contacts_paged() {
        PageRequest page = new PageRequest(0, 10);
        Page<Contact> contacts = contactRepository.findAll(page);

        assertThat(contacts.getTotalElements(), is(equalTo(2L)));
    }

    @Test
    public void it_should_return_contacts_by_functions() {
        Category category = new Category();
        category.setCategory("");
        ContactFilter contactFilter = new ContactFilter();
        contactFilter.setCategory(category);

        contactFilter.setFunctions(asList(model, salesman));

        Pageable pageable = new PageRequest(0,10);

        Page<Contact> contacts = contactRepository.findAllByFilter(contactFilter, pageable);

        assertThat(contacts.getTotalElements(), is(equalTo(2L)));
    }
}
