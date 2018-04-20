package com.angelorobson.mailinglist.dtos;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.enums.GenderEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ContactDto implements Serializable {

        private static final long serialVersionUID = -5754246207015712518L;

        private Long id;
        private String userNameInstagram;
        private GenderEnum gender;
        private UserApp userApp;
        private Category category;
        private LocalDate registrationDate;
        private List<Function> functions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNameInstagram() {
        return userNameInstagram;
    }

    public void setUserNameInstagram(String userNameInstagram) {
        this.userNameInstagram = userNameInstagram;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDto that = (ContactDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userNameInstagram, that.userNameInstagram) &&
                gender == that.gender &&
                Objects.equals(userApp, that.userApp) &&
                Objects.equals(category, that.category) &&
                Objects.equals(registrationDate, that.registrationDate) &&
                Objects.equals(functions, that.functions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userNameInstagram, gender, userApp, category, registrationDate, functions);
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "id=" + id +
                ", userNameInstagram='" + userNameInstagram + '\'' +
                ", gender=" + gender +
                ", userApp=" + userApp +
                ", category=" + category +
                ", registrationDate=" + registrationDate +
                ", functions=" + functions +
                '}';
    }
}
