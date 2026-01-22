package com.cookingflamingoz.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date; // or import from java.sql??
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "public") // keep quotes since table name is "user"
public class User implements Serializable {

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

    @Column(name = "\"instructorId\"", nullable = true)
    private Integer instructorId;

    @Column(name = "\"isVerified\"", nullable = false)
    private Boolean isVerified = false; // match DB default

    @Column(name= "\"isAdmin\"", nullable = false)
    private Boolean isAdmin = false;    //by default user isn't admin

    @Column(name = "\"isModerator\"", nullable = false)
    private Boolean isModerator = false; //by default user isn't moderator

    @OneToMany(mappedBy = "primaryKey.user")
    private Set<UserTag> tags = new  HashSet<>();


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


    public Set<UserTag> getTags() {
        return tags;
    }

    public void setTags(Set<UserTag> tags) {
        this.tags = tags;
    }

    public void addTag(UserTag tag) {
        this.tags.add(tag);
    }
}
