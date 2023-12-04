package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    Employee findByUsername(String username);
}
