package com.example.cs4500_sp19_noideainc.models;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AvailablePaymentMethods {
    Bitcoin,
    Cash,
    Check,
    CreditCard,
    Paypal,
    Sqaure,
    Venmo;
}