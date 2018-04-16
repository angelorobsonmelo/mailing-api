package com.angelorobson.mailinglist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Function implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Long id;
    private String function;
    private List<Contact> contacts;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NotEmpty(message = "Category can not be empty.")
    @Length(min = 3, max = 200, message = "Function must contain between 3 and 200 characters.")
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @ManyToMany(cascade = ALL, fetch = LAZY)
    @JsonIgnore
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", function='" + function + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function1 = (Function) o;
        return Objects.equals(id, function1.id) &&
                Objects.equals(function, function1.function) &&
                Objects.equals(contacts, function1.contacts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, function, contacts);
    }
}
