package com.angelorobson.mailinglist.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ContactSaveDto implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Optional<Long> id = Optional.empty();
    private String userNameInstagram;
    private String gender;
    private Long userAppId;
    private Long categoryId;
    private List<Long> functionsIds;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    @NotEmpty(message = "Username instagram can not be empty.")
    @Length(min = 3, max = 200, message = "Username instagram must contain between 3 and 200 characters.")
    public String getUserNameInstagram() {
        return userNameInstagram;
    }

    public void setUserNameInstagram(String userNameInstagram) {
        this.userNameInstagram = userNameInstagram;
    }

    @NotEmpty(message = "Gender can not be empty.")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NotEmpty(message = "Gender Id can not be empty.")
    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    @NotEmpty(message = "Category Id can not be empty.")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @NotEmpty(message = "Functions can not be empty.")
    public List<Long> getFunctionsIds() {
        return functionsIds;
    }

    public void setFunctionsIds(List<Long> functionsIds) {
        this.functionsIds = functionsIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactSaveDto that = (ContactSaveDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userNameInstagram, that.userNameInstagram) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(userAppId, that.userAppId) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(functionsIds, that.functionsIds);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userNameInstagram, gender, userAppId, categoryId, functionsIds);
    }

    @Override
    public String toString() {
        return "ContactSaveDto{" +
                "id=" + id +
                ", userNameInstagram='" + userNameInstagram + '\'' +
                ", gender='" + gender + '\'' +
                ", userAppId=" + userAppId +
                ", categoryId=" + categoryId +
                ", functionsIds=" + functionsIds +
                '}';
    }
}
