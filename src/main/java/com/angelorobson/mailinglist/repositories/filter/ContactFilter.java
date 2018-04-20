package com.angelorobson.mailinglist.repositories.filter;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.enums.GenderEnum;

import javax.persistence.Enumerated;
import java.util.List;

import static javax.persistence.EnumType.STRING;

public class ContactFilter {

    private String userNameInstagram;
    private GenderEnum gender;
    private Category category;
    private List<Function> functions;

    public String getUserNameInstagram() {
        return userNameInstagram;
    }

    public void setUserNameInstagram(String userNameInstagram) {
        this.userNameInstagram = userNameInstagram;
    }

    @Enumerated(STRING)
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "ContactFilter{" +
                "userNameInstagram='" + userNameInstagram + '\'' +
                ", gender=" + gender +
                ", category=" + category +
                ", functions=" + functions +
                '}';
    }
}
