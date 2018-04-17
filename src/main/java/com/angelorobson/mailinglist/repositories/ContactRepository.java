package com.angelorobson.mailinglist.repositories;

import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.contact.ContactRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long>, ContactRepositoryQuery {

}
