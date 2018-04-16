package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.builders.UserAppBuilder;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramJonh;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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

    @Before
    public void setUp() {
        Category patner = new Category();
        patner.setCategory("Patner");
       patner = categoryService.persist(patner);

        Function model = new Function();
        model.setFunction("Model");
        Function model2 = new Function();
        model2.setFunction("Model2");
        model = functionService.persist(model);
        model2 = functionService.persist(model2);

        UserApp userApp = UserAppBuilder.oneUserWithNameJoao().build();
        UserAppDto userAppDto = userAppService.persist(userApp);

       Optional<UserApp> userAppReturnd = userAppService.findById(userAppDto.getId());

        Contact john = oneContactWithUserNameInstagramJonh(patner, model, model2, userAppReturnd.get()).build();

        contactRepository.save(john);
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

        assertThat(contacts.getTotalElements(), is(equalTo(1L)));
    }
}
