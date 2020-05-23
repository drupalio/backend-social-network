package com.sergio.socialnetwork.dto;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String token;

    public UserDto() {
        super();
    }

    public UserDto(Long id, String firstName, String lastName, String token) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
