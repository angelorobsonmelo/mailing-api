package com.angelorobson.mailinglist.services;

import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactService {

    /**
     * A contact persists in the database.
     *
     * @param contact
     * @return Contact
     */
    Contact persist(Contact contact);

    /**
     * Search all users
     *
     * @param contactFilter
     * @param pageable
     * @return Page<Category>
     */
    Page<Contact> findAllByfilter(ContactFilter contactFilter, Pageable pageable);

    /**
     * A userApp edit in the database.
     *
     * @param contact
     * @return Contact
     */
     Contact edit(Contact contact);

    /**
     * Search and return a contact by ID.
     *
     * @param id
     * @return Optional<Contact>
     */
    Optional<Contact> findById(Long id);

    /**
     * Remove a contact in database.
     *
     * @param id
     */
    void remove(Long id);
}
