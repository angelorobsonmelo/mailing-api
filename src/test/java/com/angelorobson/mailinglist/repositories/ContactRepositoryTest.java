package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.builders.UserAppBuilder;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.services.CategoryService;
import com.angelorobson.mailinglist.services.FunctionService;
import com.angelorobson.mailinglist.services.UserAppService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramJonh;
import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramMary;
import static java.util.Arrays.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
   private FunctionService functionService;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    UserAppRepository userAppRepository;

    private Function locutor;

    private Function vendedor;
    private Function model;
    private Function model2;

    @Before
    public void setUp() {
        Category patner = new Category();
        patner.setCategory("Patner");
        patner = categoryService.persist(patner);

        model = new Function();
        model.setFunction("Model");
        model2 = new Function();
        model2.setFunction("Model2");
        model = functionService.persist(model);
        model2 = functionService.persist(model2);

        locutor = new Function();
        locutor.setFunction("Locutor");
        vendedor = new Function();
        vendedor.setFunction("Vendedor");
        functionService.persist(locutor);
        functionService.persist(vendedor);

        UserApp userApp = UserAppBuilder.oneUserWithNameJoao().build();
        UserAppDto userAppDto = userAppService.persist(userApp);

       Optional<UserApp> userAppReturnd = userAppService.findById(userAppDto.getId());

        Contact john = oneContactWithUserNameInstagramJonh(patner, model, model2, userAppReturnd.get()).build();
        Contact mary = oneContactWithUserNameInstagramMary(patner, locutor, vendedor, userAppReturnd.get()).build();

        contactRepository.save(asList(john, mary));
    }

    @After
    public void tearDown() {
        this.contactRepository.deleteAll();
        this.functionRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.userAppRepository.deleteAll();
    }

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

        contactFilter.setFunctions(asList(model2, vendedor));

        Pageable pageable = new PageRequest(0,10);

        Page<Contact> contacts = contactRepository.findAllByFilter(contactFilter, pageable);

        assertThat(contacts.getTotalElements(), is(equalTo(2L)));
    }
}
