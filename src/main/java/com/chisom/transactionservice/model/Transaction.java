package com.chisom.transactionservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * short message about the transaction.
     */
    @NotBlank(message = "please enter the narration")
    private String narration;

    /**
     * amount.
     */
    @NotNull(message = "please enter the amount")
    private BigDecimal amount;

    /**
     * account balance details.
     */
    @NotNull(message = "please enter the account balance")
    private BigDecimal accountBalance;

    /**
     * represents the users email.
     */
    @NotBlank(message = "please enter your email as username")
    @Email(message = "please enter a valid email")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "please enter the userId")
    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
