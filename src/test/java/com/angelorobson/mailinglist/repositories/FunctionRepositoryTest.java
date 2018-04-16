package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.entities.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FunctionRepositoryTest {

    @Autowired
    private FunctionRepository functionRepository;

    @Before
    public void setUp() {
        Function storyModel = new Function();
        Function model = new Function();
        Function photographic = new Function();

        storyModel.setFunction("Story Model");
        model.setFunction("Model");
        photographic.setFunction("Photographic");

        functionRepository.save(asList(storyModel, model, photographic));
    }

    @After
    public void tearDown() {
        this.functionRepository.deleteAll();
    }

    @Test
    public void it_should_return_function() {
        List<Function> functions = functionRepository.findAll();

        assertThat(functions.size(), is(equalTo(3)));
    }
}
