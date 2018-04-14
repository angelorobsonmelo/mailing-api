package com.angelorobson.mailinglist.services;


import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @InjectMocks
    @Autowired
    private CategoryService categoryService;

    private Category category;

    private static final Long ID = 1L;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void it_should_persist() {
        when(categoryRepository.save(category)).thenReturn(category);
        Category categorySaved = categoryService.persist(category);

        verify(categoryRepository).save(eq(category));
        assertNotNull(categorySaved);
    }

    @Test
    public void it_should_find_by_id() {
        when(categoryRepository.findOne(ID)).thenReturn(category);
        Optional<Category> categorySaved = categoryService.findById(ID);

        verify(categoryRepository).findOne(ID);
        assertNotNull(categorySaved);
    }

    @Test
    public void it_should_get_all() {
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>
                (new ArrayList<>()));

        PageRequest pageRequest = new PageRequest(0, 10);
        Page<Category> categoriesReturned = categoryService.findAll(pageRequest);

        verify(categoryRepository).findAll(eq(pageRequest));
        assertNotNull(categoriesReturned);
    }

    @Test
    public void it_should_edit() {
        when(categoryRepository.save(category)).thenReturn(category);
        Category categoryReturned = categoryService.edit(category);

        verify(categoryRepository).save(eq(category));
        assertNotNull(categoryReturned);
    }

    @Test
    public void it_should_remove() {
        categoryService.remove(ID);

        verify(categoryRepository).delete(eq(ID));
    }
}
