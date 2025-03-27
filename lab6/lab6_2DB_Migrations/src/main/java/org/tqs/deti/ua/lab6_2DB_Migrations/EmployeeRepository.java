package org.tqs.deti.ua.lab6_2DB_Migrations;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findByLastName(String lastName);

  Optional<Employee> findById(long id);
}
