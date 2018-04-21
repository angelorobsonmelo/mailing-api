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
        private UserAppDto userAppDto;
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

    public UserAppDto getUserAppDto() {
        return userAppDto;
    }

    public void setUserAppDto(UserAppDto userAppDto) {
        this.userAppDto = userAppDto;
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
                Objects.equals(userAppDto, that.userAppDto) &&
                Objects.equals(category, that.category) &&
                Objects.equals(registrationDate, that.registrationDate) &&
                Objects.equals(functions, that.functions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userNameInstagram, gender, userAppDto, category, registrationDate, functions);
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "id=" + id +
                ", userNameInstagram='" + userNameInstagram + '\'' +
                ", gender=" + gender +
                ", userAppDto=" + userAppDto +
                ", category=" + category +
                ", registrationDate=" + registrationDate +
                ", functions=" + functions +
                '}';
    }
}
