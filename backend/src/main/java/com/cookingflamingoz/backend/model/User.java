package com.cookingflamingoz.backend.model;


import jakarta.persistence.*;

import java.util.Date; // or import from java.sql??

@Entity
@Table(name = "\"user\"", schema = "public") // keep quotes since table name is "user"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"userId\"")
    private Integer userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String surname;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "\"createdAt\"", nullable = false, insertable = false, updatable = false)
    private Date createdAt;


    @Column(name = "\"enrolleeId\"", nullable = false)
    private Integer enrolleeId;

    @Column(name = "\"instructorId\"")
    private Integer instructorId;

    @Column(name = "\"isVerified\"", nullable = false)
    private Boolean isVerified = false; // match DB default


    //constructors
    public User() {} // needed for JPA

    public User(Integer id, String firstname, String surname, String password, String email, Date createdAt, Integer enrolleeId, Integer instructorId) {
        this.userId= id;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.enrolleeId = enrolleeId;
        this.instructorId = instructorId;
    }

    //getters and setters

    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getEnrolleeId() {
        return enrolleeId;
    }

    public void setEnrolleeId(Integer enrolleeId) {
        this.enrolleeId = enrolleeId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    //toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", enrolleeId=" + enrolleeId +
                ", instructorId=" + instructorId +
                '}';
    }


}
