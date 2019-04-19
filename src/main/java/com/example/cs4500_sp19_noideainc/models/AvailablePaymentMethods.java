package com.example.cs4500_sp19_noideainc.models;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum AvailablePaymentMethods {
    Bitcoin,
    Cash,
    Check,
    CreditCard,
    Paypal,
    Square,
    Venmo;
}