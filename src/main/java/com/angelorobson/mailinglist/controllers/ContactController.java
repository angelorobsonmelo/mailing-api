package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.dtos.ContactDto;
import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import com.angelorobson.mailinglist.response.Response;
import com.angelorobson.mailinglist.services.ContactService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.data.domain.Sort.Direction.valueOf;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:8080")
public class ContactController {

    private static final Logger log = getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Value("${pagination.quantity_per_page}")
    private int quantityPerPage;

    public ContactController() {}

    @PostMapping
    public ResponseEntity<Response<Page<ContactDto>>> findAllByFilter(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "userNameInstagram") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestBody ContactFilter contactFilter) {
        log.info("Searching for functions page: {}", pag);
        Response<Page<ContactDto>> response = new Response<>();

        Pageable pageRequest = new PageRequest(pag, this.quantityPerPage, valueOf(dir), ord);
        Page<Contact> contactsReturned = this.contactService.findAllByfilter(contactFilter, pageRequest);

       Page<ContactDto> contactDtos = contactsReturned.map(this::convertContactEntityToDto);

        response.setData(contactDtos);
        return ResponseEntity.ok(response);
    }

    private ContactDto convertContactEntityToDto(Contact contact) {
        UserAppDto userAppDto = new UserAppDto();
        userAppDto.setFirstName(contact.getUserApp().getFirstName());
        userAppDto.setLastName(contact.getUserApp().getLastName());
        userAppDto.setEmail(contact.getUserApp().getEmail());

        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setUserAppDto(userAppDto);
        contactDto.setFunctions(contact.getFunctions());
        contactDto.setCategory(contact.getCategory());
        contactDto.setGender(contact.getGender());
        contactDto.setUserNameInstagram(contact.getUserNameInstagram());
        return contactDto;
    }


}
