package com.angelorobson.mailinglist.entities;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Function implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private Long id;
    private String function;

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
}
