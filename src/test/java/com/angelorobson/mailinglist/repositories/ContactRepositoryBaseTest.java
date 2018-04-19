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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramJonh;
import static com.angelorobson.mailinglist.builders.ContactBuilder.oneContactWithUserNameInstagramMary;
import static java.util.Arrays.asList;

public abstract class ContactRepositoryBaseTest {

    @Autowired
     ContactRepository contactRepository;

    @Autowired
     CategoryService categoryService;

    @Autowired
     FunctionService functionService;

    @Autowired
     UserAppService userAppService;

    @Autowired
     CategoryRepository categoryRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    UserAppRepository userAppRepository;

    Function Speaker;

     Function salesman;

     Function model;

     Function modelStories;

    @Before
    public void setUp() {
        Category patner = persistCategory();
        persistFunctions();
        UserAppDto userAppDto = persistUserApp();
        persistContacts(patner, userAppDto);
    }

    private void persistContacts(Category patner, UserAppDto userAppDto) {
        Optional<UserApp> userAppReturned = userAppService.findById(userAppDto.getId());

        Contact john = oneContactWithUserNameInstagramJonh(patner, model, modelStories, userAppReturned.get()).build();
        Contact mary = oneContactWithUserNameInstagramMary(patner, Speaker, salesman, userAppReturned.get()).build();

        contactRepository.save(asList(john, mary));
    }

    private UserAppDto persistUserApp() {
        UserApp userApp = UserAppBuilder.oneUserWithNameJoao().build();
        return userAppService.persist(userApp);
    }

    private void persistFunctions() {
        model = new Function();
        model.setFunction("Model");
        modelStories = new Function();
        modelStories.setFunction("Model Stories");
        model = functionService.persist(model);
        modelStories = functionService.persist(modelStories);

        Speaker = new Function();
        Speaker.setFunction("Speaker");
        salesman = new Function();
        salesman.setFunction("salesman");
        functionService.persist(Speaker);
        functionService.persist(salesman);
    }

    private Category persistCategory() {
        Category patner = new Category();
        patner.setCategory("Patner");
        patner = categoryService.persist(patner);
        return patner;
    }

    @After
    public void tearDown() {
        contactRepository.deleteAll();
        functionRepository.deleteAll();
        categoryRepository.deleteAll();
        userAppRepository.deleteAll();
    }
}
