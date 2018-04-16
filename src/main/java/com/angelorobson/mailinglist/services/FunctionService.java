package com.angelorobson.mailinglist.services;

import com.angelorobson.mailinglist.entities.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface FunctionService {

    /**
     * A userApp persists in the database.
     *
     * @param function
     * @return Category
     */
    Function persist(Function function);

    /**
     * Search and return a user by ID.
     *
     * @param id
     * @return Optional<Function>
     */
    Optional<Function> findById(Long id);

    /**
     * Search all users
     *
     * @param pageRequest
     * @return Page<Category>
     */
    Page<Function> findAll(PageRequest pageRequest);

    /**
     * A userApp edit in the database.
     *
     * @param function
     * @return Category
     */
    Function edit(Function function);

    /**
     * Remove a category in database.
     *
     * @param id
     */
    void remove(Long id);
}
