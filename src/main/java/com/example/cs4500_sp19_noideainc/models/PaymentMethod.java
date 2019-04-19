package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AvailablePaymentMethods paymentMethod;
    @ManyToOne
    @JsonIgnore
    private User establishment;

    public PaymentMethod() {
    }

    public PaymentMethod(Integer id, AvailablePaymentMethods paymentMethod, User establishment) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.establishment = establishment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AvailablePaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(AvailablePaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getEstablishment() {
        return establishment;
    }

    public void setEstablishment(User establishment) {
        this.establishment = establishment;
    }
}