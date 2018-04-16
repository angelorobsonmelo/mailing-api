package com.angelorobson.mailinglist.entities;

import com.angelorobson.mailinglist.enums.GenderEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDate.now;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Contact implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Long id;
    private String userNameInstagram;
    private GenderEnum gender;
    private UserApp userApp;
    private Category category;
    private LocalDate registrationDate;
    private List<Function> functions;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getUserNameInstagram() {
        return userNameInstagram;
    }

    public void setUserNameInstagram(String userNameInstagram) {
        this.userNameInstagram = userNameInstagram;
    }

    @Enumerated(STRING)
    @Column(nullable = false)
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    @ManyToOne(fetch = EAGER)
    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    @ManyToOne(fetch = EAGER)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "contact_function",
            joinColumns = {@JoinColumn(name = "contact_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "function_id", nullable = false)})
    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @PrePersist
    public void prePersist() {
        final LocalDate now = now();
        this.registrationDate = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(userNameInstagram, contact.userNameInstagram) &&
                gender == contact.gender &&
                Objects.equals(userApp, contact.userApp) &&
                Objects.equals(category, contact.category) &&
                Objects.equals(registrationDate, contact.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userNameInstagram, gender, userApp, category, registrationDate);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", userNameInstagram='" + userNameInstagram + '\'' +
                ", gender=" + gender +
                ", userApp=" + userApp +
                ", category=" + category +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
