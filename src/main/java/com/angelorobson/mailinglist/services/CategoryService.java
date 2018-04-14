package com.angelorobson.mailinglist.services;

import com.angelorobson.mailinglist.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface CategoryService {

    /**
     * A userApp persists in the database.
     *
     * @param category
     * @return Category
     */
    Category persist(Category category);

    /**
     * Search and return a user by ID.
     *
     * @param id
     * @return Optional<Category>
     */
    Optional<Category> findById(Long id);

    /**
     * Search all users
     *
     * @param pageRequest
     * @return Page<Category>
     */
    Page<Category> findAll(PageRequest pageRequest);

    /**
     * A userApp edit in the database.
     *
     * @param category
     * @return Category
     */
    Category edit(Category category);

    /**
     * Remove a category in database.
     *
     * @param id
     */
    void remove(Long id);
}
