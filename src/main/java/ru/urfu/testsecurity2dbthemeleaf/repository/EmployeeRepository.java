package ru.urfu.testsecurity2dbthemeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.testsecurity2dbthemeleaf.enity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}