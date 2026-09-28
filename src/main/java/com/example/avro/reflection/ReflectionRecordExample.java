package com.example.avro.reflection;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.SchemaFormatter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

public class ReflectionRecordExample {

    public static void main(String[] args) {
        Schema schema = ReflectData.get().getSchema(CustomerRecordReflection.class);
        System.out.println("schema: " + SchemaFormatter.format("json", schema));

        try {
            File file = new File("customer-reflection.avro");
            DatumWriter<CustomerRecordReflection> datumWriter = new ReflectDatumWriter<>(
                    CustomerRecordReflection.class);
            DataFileWriter<CustomerRecordReflection> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.create(schema, file);
            dataFileWriter.append(new CustomerRecordReflection("John", "Doe", 30, "john.doe@example.com"));
            dataFileWriter.append(new CustomerRecordReflection("Teste 2", "Last Name teste", 35, "teste2@example.com"));
            dataFileWriter
                    .append(new CustomerRecordReflection("Teste 3", "Last Name teste 3", 27, "emailteste@teste.com"));
            System.out.println("Customer record successfully written to customer-reflection.avro");
            dataFileWriter.close();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing the customer record to customer-reflection.avro");
            ex.printStackTrace();
        }

        try {
            File file = new File("customer-reflection.avro");
            DatumReader<CustomerRecordReflection> datumReader = new ReflectDatumReader<>(
                    CustomerRecordReflection.class);
            DataFileReader<CustomerRecordReflection> dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                CustomerRecordReflection customerRead = dataFileReader.next();
                System.out.println("----------");
                System.out.println("Read customer record:");
                System.out.println(customerRead.toString());
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
