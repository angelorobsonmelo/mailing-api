package com.angelorobson.mailinglist.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class UserAppSaveDto {

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

  @NotEmpty(message = "Firstname can not be empty.")
  @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @NotEmpty(message = "Lastname can not be empty.")
  @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }




  @NotEmpty(message = "Email can not be empty.")
  @Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
  @Email(message = "Invalid email.")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @NotEmpty(message = "Password can not be empty.")
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
