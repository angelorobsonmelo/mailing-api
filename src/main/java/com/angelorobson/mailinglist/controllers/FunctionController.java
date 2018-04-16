package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.response.Response;
import com.angelorobson.mailinglist.services.FunctionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.data.domain.Sort.Direction.*;

@RestController
@RequestMapping("/functions")
@CrossOrigin(origins = "http://localhost:8080")
public class FunctionController {

  private static final Logger log = getLogger(FunctionController.class);

  @Autowired
  private FunctionService functionService;

  @Value("${pagination.quantity_per_page}")
  private int quantityPerPage;

  public FunctionController() {
  }

  /**
   * Return list of function
   *
   * @return ResponseEntity<Response<Page<Function>>>
   */
  @GetMapping
  public ResponseEntity<Response<Page<Function>>> findAll(
    @RequestParam(value = "pag", defaultValue = "0") int pag,
    @RequestParam(value = "ord", defaultValue = "category") String ord,
    @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
    log.info("Searching for functions page: {}", pag);
    Response<Page<Function>> response = new Response<>();

    PageRequest pageRequest = new PageRequest(pag, this.quantityPerPage, valueOf(dir), ord);
    Page<Function> userAppDtos = this.functionService.findAll(pageRequest);

    response.setData(userAppDtos);
    return ResponseEntity.ok(response);
  }

  /**
   * Adds a new user.
   *
   * @param function
   * @param result
   * @return ResponseEntity<Response<UserAppDto>>
   */
  @PostMapping
  public ResponseEntity<Response<Function>> save(@Valid @RequestBody Function function,
                                                   BindingResult result) {
    log.info("Adding Category: {}", function.toString());
    Response<Function> response = new Response<>();
    validateUser(function, result);

    if (hasErrors(result, response)) return ResponseEntity.badRequest().body(response);

    Function categorySaved = this.functionService.persist(function);
    response.setData(categorySaved);
    return ResponseEntity.ok(response);
  }

  /**
   * Remove a user by ID.
   *
   * @param id
   * @return ResponseEntity<Response<String>>
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
    log.info("Removing category: {}", id);
    Response<String> response = new Response<>();
    Optional<Function> functionReturned = this.functionService.findById(id); 

    if (!functionReturned.isPresent()) {
      log.info("Error removing because user ID: {} must be invalid.", id);
      response.getErrors().add("Error removing category. Record not found for id " + id);
      return ResponseEntity.badRequest().body(response);
    }

    this.functionService.remove(id);
    return ResponseEntity.ok(new Response<>());
  }


  /**
   * Updates a user's data.
   *
   * @param id
   * @param function
   * @return ResponseEntity<Response<Category>>
   * @throws ParseException
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Response<Function>> update(@PathVariable("id") Long id,
                                                     @Valid @RequestBody Function function, BindingResult result) {
    log.info("Updates a function's data.: {}", function.toString());
    Response<Function> response = new Response<>();
    validateUser(function, result);
    function.setId(id);

    Optional<Function> functionReturned = this.functionService.findById(function.getId());

    if (functionReturned.isPresent()) {
       copyProperties(function, functionReturned.get(), "id");
      Function categoryEdited = this.functionService.edit(function);
        response.setData(categoryEdited);

    } else {
      result.addError(new ObjectError("category", "Category not found."));
    }

    if (hasErrors(result, response)) return ResponseEntity.badRequest().body(response);

    return ResponseEntity.ok(response);
  }


  /**
   * Validate a user, verifying that it is existing and valid on the system.
   *
   * @param o
   * @param result
   */
  private void validateUser(Object o, BindingResult result) {
    if (o == null) {
      result.addError(new ObjectError("category", "Category not informed."));
    }
  }


  private boolean hasErrors(BindingResult result, Response<Function> response) {
    if (result.hasErrors()) {
      log.error("Error validating user: {}", result.getAllErrors());
      if (result.hasErrors()) {
        log.error("Error validating user: {}", result.getAllErrors());
        result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
        return true;
      }
    }
    return false;
  }

}
