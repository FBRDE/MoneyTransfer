package com.openclassrooms.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="transaction_date")
    private LocalDateTime transactionDate;

    @Column(name="amount")
    private double amount;

    @Column(name="description")
    private String description;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
   @JoinColumn(name="sender", referencedColumnName = "id")
    private User sender;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="receiver", referencedColumnName = "id")
    private User receiver;


}