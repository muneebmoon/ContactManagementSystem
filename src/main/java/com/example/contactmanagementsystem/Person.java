package com.example.contactmanagementsystem;
import javafx.beans.property.*;

public class Person {
    private IntegerProperty id;
    private StringProperty name;
    private LongProperty phoneNumber;
    private StringProperty email;

    public Person(int id,  String name, Long phoneNumber, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleLongProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    public String getName() {
        return name.get();
    }

    public int getId() {
        return id.get();
    }

    public long getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public LongProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty emailProperty() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "', phoneNumber=" + phoneNumber + ", email='" + email + "'}";
    }
}