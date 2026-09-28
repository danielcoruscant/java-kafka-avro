package com.example.avro.specific;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.example.simple.Customer;

public class SpecificRecordExample {

    public static void main(String[] args) {

        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setAge(30);
        customerBuilder.setHeight(175.5f);
        customerBuilder.setWeight(80.5f);
        customerBuilder.setCustomerEmail(List.of("john_due@emample.com"));

        Customer customer = customerBuilder.build();
        System.out.println(customer);

        final DatumWriter<Customer> datumWriter = new SpecificDatumWriter<Customer>();
        try {
            DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
            dataFileWriter.append(customer);
            dataFileWriter.append(customer);
            System.out.println("Customer record successfully written to customer-specific.avro");
            dataFileWriter.close();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing the customer record to customer-specific.avro");
            ex.printStackTrace();
        }

        try {
            File file = new File("customer-specific.avro");
            DatumReader<Customer> datumReader = new SpecificDatumReader<>(customer.getSchema());
            DataFileReader<Customer> dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                Customer customerRead = dataFileReader.next();
                System.out.println("Read customer record:");
                System.out.println(customerRead);
                System.out.println("----------");
                System.err.println("Customer name:");
                System.err.println(customerRead.getFirstName() + " " + customerRead.getLastName());
            }
            dataFileReader.close();
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the customer record from customer-generic.avro");
            ex.printStackTrace();
        }

    }

}
