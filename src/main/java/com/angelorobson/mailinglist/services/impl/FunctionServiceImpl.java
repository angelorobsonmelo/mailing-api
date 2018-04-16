package com.angelorobson.mailinglist.services.impl;

import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.repositories.FunctionRepository;
import com.angelorobson.mailinglist.services.FunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class FunctionServiceImpl implements FunctionService {

    private static final Logger log = LoggerFactory.getLogger(FunctionServiceImpl.class);

    @Autowired
    private FunctionRepository functionRepository;

    @Override
    public Function persist(Function function) {
        log.info("Persisting a category {}", function);
        Function categoryReturnd = functionRepository.save(function);
        return categoryReturnd;
    }

    @Override
    public Optional<Function> findById(Long id) {
        log.info("Finding a Category by ID: {}", id);
        return ofNullable(functionRepository.findOne(id));
    }

    @Override
    public Page<Function> findAll(PageRequest pageRequest) {
        log.info("Get all categories {}", pageRequest);
        Page<Function> categoriesReturned = functionRepository.findAll(pageRequest);
        return categoriesReturned;
    }

    @Override
    public Function edit(Function function) {
        log.info("Editing a category {}", function);
        Function categoryReturnd = functionRepository.save(function);
        return categoryReturnd;
    }

    @Override
    public void remove(Long id) {
        log.info("Removing a category with {}", id);
        functionRepository.delete(id);
    }
}
