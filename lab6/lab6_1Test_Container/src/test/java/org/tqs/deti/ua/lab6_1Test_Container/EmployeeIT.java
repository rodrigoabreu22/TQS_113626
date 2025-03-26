package org.tqs.deti.ua.lab6_1Test_Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

@SpringBootTest(classes = Lab61containerApplication.class)
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeIT {

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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    @Order(1)
    public void addNewEmployee() {
        System.out.println("Employee Repository: " + employeeRepository);

        if (employeeRepository == null) {
            throw new IllegalStateException("EmployeeRepository is not injected!");
        }

        Employee emp = new Employee("Lebron", "James", 100000, "player");
        employeeRepository.save(emp);
        System.out.println(String.format("%s saved!", emp.toString()));
    }

    @Test
    @Order(2)
    public void fetchEmployee() {
        Employee emp = employeeRepository.findAll().get(0);

        System.out.println(String.format("%s fetched!", emp));

        assertTrue(emp.getFirstName().equals("Lebron"));
        assertTrue(emp.getLastName().equals("James"));
        assertTrue(emp.getRole().equals("player"));
        assertTrue(emp.getSalary()==100000);
    }

    @Test
    @Order(3)
    public void updateEmployee_thenFetchEmployee(){
        Employee emp = employeeRepository.findAll().get(0);

        System.out.println(String.format("%s fetched!", emp));

        emp.setRole("my sunshine");
        employeeRepository.save(emp);

        System.out.println(String.format("%s updated!", emp));

        assertTrue(emp.getFirstName().equals("Lebron"));
        assertTrue(emp.getLastName().equals("James"));
        assertTrue(emp.getRole().equals("my sunshine"));
        assertTrue(emp.getSalary()==100000);
    }
}
