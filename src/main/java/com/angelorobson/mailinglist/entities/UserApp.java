package com.angelorobson.mailinglist.entities;

import com.angelorobson.mailinglist.enums.ProfileEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_app")
public class UserApp implements Serializable {

  private static final long serialVersionUID = -5754246207015712518L;

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private ProfileEnum profile;
  private List<Contact> contacts;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(nullable = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public ProfileEnum getProfile() {
    return profile;
  }

  public void setProfile(ProfileEnum profile) {
    this.profile = profile;
  }

  @OneToMany(mappedBy = "userApp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserApp userApp = (UserApp) o;
    return Objects.equals(id, userApp.id) &&
            Objects.equals(firstName, userApp.firstName) &&
            Objects.equals(lastName, userApp.lastName) &&
            Objects.equals(email, userApp.email) &&
            Objects.equals(password, userApp.password) &&
            profile == userApp.profile;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, firstName, lastName, email, password, profile);
  }

  @Override
  public String toString() {
    return "UserApp{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", profile=" + profile +
            '}';
  }
}
