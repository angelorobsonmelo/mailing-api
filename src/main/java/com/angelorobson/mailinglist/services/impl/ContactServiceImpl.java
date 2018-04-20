package com.angelorobson.mailinglist.services.impl;

import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.ContactRepository;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import com.angelorobson.mailinglist.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact persist(Contact contact) {
        log.info("Persisting a contact {}", contact);
        Contact contactReturnd = contactRepository.save(contact);
        return contactReturnd;
    }

    @Override
    public Page<Contact> findAllByfilter(ContactFilter contactFilter, Pageable pageable) {
        log.info("Searching all contacts by filters {}", contactFilter);
        return contactRepository.findAllByFilter(contactFilter, pageable);
    }

    @Override
    public Contact edit(Contact contact) {
        log.info("Editing a contact {}", contact);
        Contact contactReturned = contactRepository.save(contact);
        return contactReturned;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.info("Searching a contact by id {}", id);
        Contact contact = contactRepository.findOne(id);
        return Optional.ofNullable(contact);
    }

    @Override
    public void remove(Long id) {
        log.info("Removing a contact with {}", id);
        contactRepository.delete(id);
    }
}
