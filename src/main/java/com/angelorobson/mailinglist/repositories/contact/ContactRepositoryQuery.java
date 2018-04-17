package com.angelorobson.mailinglist.repositories.contact;

import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import com.angelorobson.mailinglist.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepositoryQuery {

    Page<Contact> findAllByFilter(ContactFilter contactFilter, Pageable page);

}
