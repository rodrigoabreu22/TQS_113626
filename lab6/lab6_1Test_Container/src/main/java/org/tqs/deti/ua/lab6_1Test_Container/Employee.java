package org.tqs.deti.ua.lab6_1Test_Container;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private double salary;
    private String role;

    public Employee(){
        this.firstName = "default";
        this.lastName = "default";
        this.salary = 0;
        this.role = "default";
    }
    
    public Employee(String firstName, String lastName, double salary, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.role = role;
    }

    //getters
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getSalary() {
        return salary;
    }
    public String getRole() {
        return role;
    }

    
    //setters
    public void setId(long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
                + ", role=" + role + "]";
    }
}
