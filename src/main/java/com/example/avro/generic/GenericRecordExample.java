package com.example.avro.generic;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

public class GenericRecordExample {

    public static void main(String[] args) {

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" + //
                "        \"type\": \"record\",\n" + //
                "        \"namespace\": \"com.example\",\n" + //
                "        \"name\": \"Customer\",\n" + //
                "        \"doc\": \"Avro Schema for our Customer\",\n" + //
                "        \"fields\": [\n" + //
                "            {\n" + //
                "                \"name\": \"first_name\",\n" + //
                "                \"type\": \"string\",\n" + //
                "                \"doc\": \"First name of the customer\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"last_name\",\n" + //
                "                \"type\": \"string\",\n" + //
                "                \"doc\": \"Last name of the customer\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"age\",\n" + //
                "                \"type\": \"int\",\n" + //
                "                \"doc\": \"Age of the customer\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"height\",\n" + //
                "                \"type\": \"float\",\n" + //
                "                \"doc\": \"Height of the customer in centimeters\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"weight\",\n" + //
                "                \"type\": \"float\",\n" + //
                "                \"doc\": \"Weight of the customer in kilograms\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"automated_email\",\n" + //
                "                \"type\": \"boolean\",\n" + //
                "                \"default\": false,\n" + //
                "                \"doc\": \"Indicates if the customer has opted for automated emails\"\n" + //
                "            },\n" + //
                "            {\n" + //
                "                \"name\": \"customer_email\",\n" + //
                "                \"type\": {\n" + //
                "                    \"type\": \"array\",\n" + //
                "                    \"items\": \"string\"\n" + //
                "                },\n" + //
                "                \"doc\": \"List of customer email addresses\"\n" + //
                "            }\n" + //
                "        ]\n" + //
                "    }");

        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "John");
        customerBuilder.set("last_name", "Doe");
        customerBuilder.set("age", 25);
        customerBuilder.set("height", 170f);
        customerBuilder.set("weight", 80.5f);
        customerBuilder.set("customer_email", Arrays.asList("john.doe@example.com"));
        GenericRecord customerRecord = customerBuilder.build();
        System.out.println(customerRecord);

        final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try {
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.create(schema, new File("customer-generic.avro"));
            dataFileWriter.append(customerRecord);
            dataFileWriter.append(customerRecord);
            dataFileWriter.append(customerRecord);
            System.out.println("Customer record successfully written to customer-generic.avro");
            dataFileWriter.close();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing the customer record to customer-generic.avro");
            ex.printStackTrace();
        }

        try {
            File file = new File("customer-generic.avro");
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                GenericRecord customer = dataFileReader.next();
                System.out.println("Read customer record:");
                System.out.println(customer);
                System.out.println("----------");
                System.err.println("Customer name:");
                System.err.println(customer.get("first_name") + " " + customer.get("last_name"));
            }
            dataFileReader.close();
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the customer record from customer-generic.avro");
            ex.printStackTrace();
        }

    }

}
