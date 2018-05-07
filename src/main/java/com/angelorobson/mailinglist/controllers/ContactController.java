package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.dtos.ContactDto;
import com.angelorobson.mailinglist.dtos.ContactSaveDto;
import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.enums.GenderEnum;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.Sort.Direction.valueOf;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private static final Logger log = getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Value("${pagination.quantity_per_page}")
    private int quantityPerPage;

    public ContactController() {}

    @PostMapping(value = "/filter")
    public ResponseEntity<Response<Page<ContactDto>>> findAllByFilter(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage,
            @RequestBody ContactFilter contactFilter) {
        log.info("Searching for functions page: {}", pag);
        Response<Page<ContactDto>> response = new Response<>();

        Pageable pageRequest = new PageRequest(pag, Integer.valueOf(perPage), valueOf(dir), ord);
        Page<Contact> contactsReturned = this.contactService.findAllByfilter(contactFilter, pageRequest);

       Page<ContactDto> contactDtos = contactsReturned.map(this::convertContactEntityToDto);

        response.setData(contactDtos);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<ContactDto>> save(
            @RequestBody ContactSaveDto contactSaveDto, BindingResult result) {
        log.info("Persisting a contact : {}", contactSaveDto.toString());
        Response<ContactDto> response = new Response<>();
        validateUser(contactSaveDto, result);

        Contact contact = convertContactSaveDtoToEntity(contactSaveDto, result);
        Contact contactReturned = this.contactService.persist(contact);

        ContactDto contactDto = convertContactEntityToDto(contactReturned);

        response.setData(contactDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ContactDto>> findById(@PathVariable("id") Long id) {
        log.info("Searching by id: {}", id);
        Response<ContactDto> response = new Response<>();

        Optional<Contact> contactDataBaseReturned = this.contactService.findById(id);
        if (contactDataBaseReturned.isPresent()) {
          ContactDto contactDto = convertContactEntityToDto(contactDataBaseReturned.get());
            response.setData(contactDto);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ContactDto>> update(@PathVariable("id") Long id,
            @RequestBody ContactSaveDto contactSaveDto, BindingResult result) {
        log.info("Updating a contact : {}", contactSaveDto.toString());
        Response<ContactDto> response = new Response<>();
        validateUser(contactSaveDto, result);
        contactSaveDto.setId(Optional.of(id));

        Contact contact = convertContactSaveDtoToEntity(contactSaveDto, result);

        Contact contactReturned = this.contactService.edit(contact);

        ContactDto contactDto = convertContactEntityToDto(contactReturned);

        response.setData(contactDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
        log.info("Removing category: {}", id);
        Response<String> response = new Response<>();
        Optional<Contact> contactReturned = this.contactService.findById(id);

        if (!contactReturned.isPresent()) {
            log.info("Error removing because user ID: {} must be invalid.", id);
            response.getErrors().add("Error removing category. Record not found for id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.contactService.remove(id);
        return ResponseEntity.ok(new Response<>());
    }

    private Contact convertContactSaveDtoToEntity(ContactSaveDto contactSaveDto, BindingResult result) {
        Contact contact = getContactEntityFromDto(contactSaveDto);

        if (contactSaveDto.getId().isPresent()) { 
            Optional<Contact> contactDataBaseReturned = this.contactService.findById(contactSaveDto.getId().get());
            if (contactDataBaseReturned.isPresent()) {
                Contact contactEntityFromDto = getContactEntityFromDto(contactSaveDto);

                contactDataBaseReturned.get().setCategory(contactEntityFromDto.getCategory());
                contactDataBaseReturned.get().setUserNameInstagram(contactEntityFromDto.getUserNameInstagram());
                contactDataBaseReturned.get().setFunctions(contactEntityFromDto.getFunctions());
                contactDataBaseReturned.get().setGender(contactEntityFromDto.getGender());

                return  contactDataBaseReturned.get();
            } else {
                result.addError(new ObjectError("user", "User not found."));
            }
        }

        return contact;
    }

    private Contact getContactEntityFromDto(ContactSaveDto contactSaveDto) {
        Contact contact = new Contact();
        List<Function> functions = convertToFunctionsEntity(contactSaveDto.getFunctionsIds());
        UserApp userApp = new UserApp();
        userApp.setId(contactSaveDto.getUserAppId());
        Category category = new Category();
        category.setId(contactSaveDto.getCategoryId());

        contact.setUserApp(userApp);
        contact.setCategory(category);
        contact.setFunctions(functions);
        contact.setGender(GenderEnum.valueOf(contactSaveDto.getGender()));
        contact.setUserNameInstagram(contactSaveDto.getUserNameInstagram());
        return contact;
    }

    private List<Function> convertToFunctionsEntity(List<Long> functionsId) {
        List<Function> functions = new ArrayList<>();
        Function function;

        for (Long id: functionsId) {
            function = new Function();
            function.setId(id);

            functions.add(function);
        }

        return functions;
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

    /**
     * Validate a user, verifying that it is existing and valid on the system.
     *
     * @param o
     * @param result
     */
    private void validateUser(Object o, BindingResult result) {
        if (o == null) {
            result.addError(new ObjectError("user", "User not informed."));
        }
    }


}
