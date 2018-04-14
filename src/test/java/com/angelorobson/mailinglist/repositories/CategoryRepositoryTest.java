package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.entities.Category;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository cateogoryRepository;

    @Before
    public void setUp() {
        Category partner = new Category();
        Category candidate = new Category();

        partner.setCategory("Partner");
        candidate.setCategory("Candidate");

        cateogoryRepository.save(asList(partner, candidate));
    }

    @After
    public void tearDown() {
        this.cateogoryRepository.deleteAll();
    }

    @Test
    public void it_should_return_categories() {
        List<Category> categories = cateogoryRepository.findAll();

        assertThat(categories.size(), is(equalTo(2)));
    }
}
