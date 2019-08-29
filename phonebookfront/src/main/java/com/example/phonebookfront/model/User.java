package com.example.phonebookfront.model;

import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank(message =  "Username is mandatory")
    private String name;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
