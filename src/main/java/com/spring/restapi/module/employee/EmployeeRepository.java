package com.spring.restapi.module.employee;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findOneByEmployeeId(Long id);
}
