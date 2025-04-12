package com.naveend3v.readerhub.dto.userdetails;

public class UserDetailsDto {

    private Integer userId;
    private String username;
    private String roles;
    private String email;

    public UserDetailsDto(){}

    public UserDetailsDto(Integer userId, String username, String roles, String email) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.email = email;
    }

    public UserDetailsDto(Integer id, String name, String email) {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
