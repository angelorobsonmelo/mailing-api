package com.angelorobson.mailinglist.controllers;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.response.Response;
import com.angelorobson.mailinglist.services.CategoryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {

  private static final Logger log = getLogger(CategoryController.class);

  @Autowired
  private CategoryService categoryService;

  @Value("${pagination.quantity_per_page}")
  private int quantityPerPage;

  public CategoryController() {
  }

  /**
   * Return list of users
   *
   * @return ResponseEntity<Response<Page<UserAppDto>>>
   */
  @GetMapping
  public ResponseEntity<Response<Page<Category>>> findAll(
    @RequestParam(value = "pag", defaultValue = "0") int pag,
    @RequestParam(value = "ord", defaultValue = "id") String ord,
    @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
    log.info("Searching for users page: {}", pag);
    Response<Page<Category>> response = new Response<>();

    PageRequest pageRequest = new PageRequest(pag, this.quantityPerPage, Sort.Direction.valueOf(dir), ord);
    Page<Category> userAppDtos = this.categoryService.findAll(pageRequest);

    response.setData(userAppDtos);
    return ResponseEntity.ok(response);
  }


  /**
   * Adds a new user.
   *
   * @param category
   * @param result
   * @return ResponseEntity<Response<UserAppDto>>
   */


  @PostMapping
  public ResponseEntity<Response<Category>> save(@Valid @RequestBody Category category,
                                                   BindingResult result) {
    log.info("Adding Category: {}", category.toString());
    Response<Category> response = new Response<>();
    validateUser(category, result);

    if (hasErrors(result, response)) return ResponseEntity.badRequest().body(response);

    Category categorySaved = this.categoryService.persist(category);
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
    Optional<Category> categoryReturned = this.categoryService.findById(id);

    if (!categoryReturned.isPresent()) {
      log.info("Error removing because user ID: {} must be invalid.", id);
      response.getErrors().add("Error removing category. Record not found for id " + id);
      return ResponseEntity.badRequest().body(response);
    }

    this.categoryService.remove(id);
    return ResponseEntity.ok(new Response<>());
  }


  /**
   * Updates a user's data.
   *
   * @param id
   * @param category
   * @return ResponseEntity<Response<Category>>
   * @throws ParseException
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Response<Category>> update(@PathVariable("id") Long id,
                                                     @Valid @RequestBody Category category, BindingResult result) {
    log.info("Updates a category's data.: {}", category.toString());
    Response<Category> response = new Response<>();
    validateUser(category, result);
    category.setId(id);

    Optional<Category> userReturned = this.categoryService.findById(category.getId());

    if (userReturned.isPresent()) {
       copyProperties(category, userReturned.get(), "id");
        Category categoryEdited = this.categoryService.edit(category);
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


  private boolean hasErrors(BindingResult result, Response<Category> response) {
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
