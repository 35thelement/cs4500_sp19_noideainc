package com.example.cs4500_sp19_noideainc.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressTests {

    @Test
    public void testSettersAndGetters() {
        Address address = new Address();

        address.setState("AZ");
        address.setCity("Phoenix");
        address.setZip("85085");
        address.setId(212);
        address.setStreet("35 West Street");

        User steve = new User(2, UserType.Client, "steve", "password", "Steve", "Harrison");
        address.setResident(steve);

        assertEquals(212, address.getId().intValue());
        assertEquals("Phoenix", address.getCity());
        assertEquals("85085", address.getZip());
        assertEquals("AZ", address.getState());
        assertEquals("35 West Street", address.getStreet());
        assertSame(steve, address.getResident());
    }
}
