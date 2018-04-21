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
import org.h2.engine.User;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
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

    @PostMapping("filter")
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

    @PostMapping
    public ResponseEntity<Response<ContactDto>> save(
            @RequestBody ContactSaveDto contactSaveDto, BindingResult result) {
        log.info("Persisting a contact : {}", contactSaveDto.toString());
        Response<ContactDto> response = new Response<>();
        validateUser(contactSaveDto, result);

        Contact contact = convertContactSaveDtoToEntity(contactSaveDto);
        Contact contactReturned = this.contactService.persist(contact);

        ContactDto contactDto = convertContactEntityToDto(contactReturned);

        response.setData(contactDto);
        return ResponseEntity.ok(response);
    }

    private Contact convertContactSaveDtoToEntity(ContactSaveDto contactSaveDto) {
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
