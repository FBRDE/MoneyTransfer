package com.openclassrooms.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(name="birthday")
    private Date birthday;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="balance")
    private double balance;

    @Column(name="swift")
    private String swift;

    @Column(name="account_number")
    private String accountNumber;



    @ManyToMany(
             fetch = FetchType.LAZY,
          //  fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "connection",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "friend")

    )

    private List<User> friends = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="sender", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToMany(

            fetch =FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id_role"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new ArrayList<>();


}