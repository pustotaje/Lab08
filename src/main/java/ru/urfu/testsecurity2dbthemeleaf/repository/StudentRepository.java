package ru.urfu.testsecurity2dbthemeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.testsecurity2dbthemeleaf.enity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
