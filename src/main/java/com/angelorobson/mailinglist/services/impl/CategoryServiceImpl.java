package com.angelorobson.mailinglist.services.impl;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.repositories.CategoryRepository;
import com.angelorobson.mailinglist.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category persist(Category category) {
        log.info("Persisting a category {}", category);
        Category categoryReturnd = categoryRepository.save(category);
        return categoryReturnd;
    }

    @Override
    public Optional<Category> findById(Long id) {
        log.info("Finding a Category by ID: {}", id);
        return ofNullable(categoryRepository.findOne(id));
    }

    @Override
    public Page<Category> findAll(PageRequest pageRequest) {
        log.info("Get all categories {}", pageRequest);
        Page<Category> categoriesReturned = categoryRepository.findAll(pageRequest);
        return categoriesReturned;
    }

    @Override
    public Category edit(Category category) {
        log.info("Editing a category {}", category);
        Category categoryReturnd = categoryRepository.save(category);
        return categoryReturnd;
    }

    @Override
    public void remove(Long id) {
        log.info("Removing a category with {}", id);
        categoryRepository.delete(id);
    }
}
