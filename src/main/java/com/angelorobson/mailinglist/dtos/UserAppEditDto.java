package com.angelorobson.mailinglist.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class UserAppEditDto {

  private Optional<Long> id = Optional.empty();
  private String firstName;
  private String lastName;
  private String email;
  private String password;


  public Optional<Long> getId() {
    return id;
  }

  public void setId(Optional<Long> id) {
    this.id = id;
  }

  @NotEmpty(message = "Name can not be empty.")
  @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @NotEmpty(message = "Name can not be empty.")
  @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public String toString() {
    return "UserAppSaveDto{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
