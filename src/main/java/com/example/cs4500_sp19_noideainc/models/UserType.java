package com.example.cs4500_sp19_noideainc.models;

import com.fasterxml.jackson.annotation.JsonFormat;

/* Types of Users */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    Client("Client"),
    Provider("Provider");

    private String type;

    private UserType(String type) {
        this.type = type;
    }


}