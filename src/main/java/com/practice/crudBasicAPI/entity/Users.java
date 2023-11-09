package com.practice.crudBasicAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(unique=true)
//    private String username;
    @Column(unique=true)
    private String email;
    private String password;
    private String fullname;
//    private String lname;
//    private String mname;
    private String phoneNumber;
//    private Roles role;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime date_registered;
    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime date_modified;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Orders> orders;

}
//enum Roles {
//    admin, user;
//}