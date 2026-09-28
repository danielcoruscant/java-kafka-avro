package com.example.avro.reflection;

public class CustomerRecordReflection {

    private String firstName;
    private String lastName;
    private int age;
    private String email;

    public CustomerRecordReflection() {
    }
    
    public CustomerRecordReflection(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerRecordReflection [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
                + ", email=" + email + "]";
    }

}
