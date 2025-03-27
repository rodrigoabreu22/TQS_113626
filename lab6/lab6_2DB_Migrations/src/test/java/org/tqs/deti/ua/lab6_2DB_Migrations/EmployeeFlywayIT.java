package org.tqs.deti.ua.lab6_2DB_Migrations;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.tqs.deti.ua.lab6_2DB_Migrations.Employee;
import org.tqs.deti.ua.lab6_2DB_Migrations.EmployeeRepository;
import org.tqs.deti.ua.lab6_2DB_Migrations.Lab61containerApplication;

@SpringBootTest(classes = Lab61containerApplication.class)
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeFlywayIT {

    @SuppressWarnings({ "rawtypes", "resource" })
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
        .withUsername("rodrigo")
        .withPassword("pwd")
        .withDatabaseName("lab6_1test");

    @Autowired
    EmployeeRepository employeeRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    public void addNewEmployee() {
        Employee emp = new Employee("Bronny", "James", 1000, "player");

        employeeRepository.save(emp);

        System.out.println(String.format("%s saved!", emp.toString()));

        Optional<Employee> emp_fetched = employeeRepository.findById(emp.getId());

        assertTrue(emp_fetched.isPresent());
        assertEquals(emp, emp_fetched.get());
    }

    @Test
    @Order(2)
    public void fetchEmployee() {
        Employee emp = employeeRepository.findAll().get(0);

        System.out.println(String.format("%s fetched!", emp));

        assertTrue(emp.getFirstName().equals("Lebron"));
        assertTrue(emp.getLastName().equals("James"));
        assertTrue(emp.getRole().equals("player"));
        assertTrue(emp.getSalary()==1000000);
    }

    @Test
    @Order(3)
    public void updateEmployee_thenFetchEmployee(){
        Employee emp = employeeRepository.findAll().get(0);

        System.out.println(String.format("%s fetched!", emp));

        emp.setRole("my sunshine");
        employeeRepository.save(emp);

        Employee emp_fetched = employeeRepository.findAll().get(0);

        System.out.println(String.format("%s updated!", emp_fetched));

        assertTrue(emp.getFirstName().equals("Lebron"));
        assertTrue(emp.getLastName().equals("James"));
        assertTrue(emp.getRole().equals("my sunshine"));
        assertTrue(emp.getSalary()==1000000);
    }

    @Test
    @Order(4)
    public void removeEmployee() {
        Employee emp = employeeRepository.findByLastName("Doncic").get(0);
    
        System.out.println(String.format("%s to be removed!", emp));
    
        long id = emp.getId();
        employeeRepository.delete(emp);
    
        Optional<Employee> emp_fetched = employeeRepository.findById(id);
    
        assertTrue(emp_fetched.isEmpty());
    }

}
