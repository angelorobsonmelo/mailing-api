package com.angelorobson.mailinglist.services;


import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.repositories.FunctionRepository;
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
public class FunctionServiceTest {

    @MockBean
    private FunctionRepository functionRepository;

    @InjectMocks
    @Autowired
    private FunctionService functionService;

    private Function function;

    private static final Long ID = 1L;

    @Before
    public void setUp() {
        function = new Function();
    }

    @Test
    public void it_should_persist() {
        when(functionRepository.save(function)).thenReturn(function);
        Function functionSaved = functionService.persist(function);

        verify(functionRepository).save(eq(function));
        assertNotNull(functionSaved);
    }

    @Test
    public void it_should_find_by_id() {
        when(functionRepository.findOne(ID)).thenReturn(function);
        Optional<Function> functionSaved = functionService.findById(ID);

        verify(functionRepository).findOne(ID);
        assertNotNull(functionSaved);
    }

    @Test
    public void it_should_get_all() {
        when(functionRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>
                (new ArrayList<>()));

        PageRequest pageRequest = new PageRequest(0, 10);
        Page<Function> categoriesReturned = functionService.findAll(pageRequest);

        verify(functionRepository).findAll(eq(pageRequest));
        assertNotNull(categoriesReturned);
    }

    @Test
    public void it_should_edit() {
        when(functionRepository.save(function)).thenReturn(function);
        Function functionReturned = functionService.edit(function);

        verify(functionRepository).save(eq(function));
        assertNotNull(functionReturned);
    }

    @Test
    public void it_should_remove() {
        functionService.remove(ID);

        verify(functionRepository).delete(eq(ID));
    }
}
