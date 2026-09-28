package com.example;

import com.example.complex.AddressType;
import com.example.complex.Customer;
import com.example.complex.CustomerAddress;

public class App {
    public static void main(String[] args) {
        Customer customer = Customer.newBuilder()
                .setFirstName("John")
                .setLastName("Doe")
                .setAge(30)
                .setHeight(180.5f)
                .setWeight(75.0f)
                .setAutomatedEmail(true)
                .setCustomerEmail(java.util.List.of("john.doe@example.com"))
                .setCustomerAddress(CustomerAddress.newBuilder()
                        .setAddress("123 Main St")
                        .setCity("Springfield")
                        .setPostcode(12345)
                        .setType(AddressType.RESIDENCIAL)
                        .build())
                .build();

        System.out.println(customer);
    }
}
