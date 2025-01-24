package ru.urfu.testsecurity2dbthemeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.testsecurity2dbthemeleaf.enity.Present;

@Repository
public interface RoleRepository extends JpaRepository<Present, Long> {
    Present findByName(String name);
}