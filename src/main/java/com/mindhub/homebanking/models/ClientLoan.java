package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double amount;
    private double payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Client user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(double amount, double payments) {
        this.amount = amount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public double getPayments() {
        return payments;
    }

    public Client getUser() {
        return user;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(double payments) {
        this.payments = payments;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
