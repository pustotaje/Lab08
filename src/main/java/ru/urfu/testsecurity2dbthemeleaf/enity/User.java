package ru.urfu.testsecurity2dbthemeleaf.enity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter  // Генерирует геттеры для всех полей
@Setter  // Генерирует сеттеры для всех полей
@Entity  // Указывает, что класс является сущностью JPA
@NoArgsConstructor  // Генерирует конструктор без параметров
@AllArgsConstructor  // Генерирует конструктор с параметрами
@Table(name = "users")  // Указывает имя таблицы в базе данных
public class User {

    @Id  // Указывает, что это основной ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автогенерация значения ID
    private Long id;

    @Column(nullable = false)  // Указывает, что поле не может быть пустым
    private String name;

    @Column(nullable = false, unique = true)  // Указывает, что поле уникально и не может быть пустым
    private String email;

    @Column(nullable = false)  // Указывает, что поле не может быть пустым
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)  // Указывает, что это коллекция элементов (роли пользователя)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))  // Таблица для хранения ролей
    @Column(name = "role")  // Столбец, содержащий роли
    private Set<String> roles = new HashSet<>();  // Хранит роли пользователя (например, "USER", "ADMIN")

    // Геттеры и сеттеры для всех полей

    public Set<String> getRoles() {
        return roles;  // Возвращает роли пользователя
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;  // Устанавливает роли пользователя
    }

    public Long getId() {
        return id;  // Возвращает ID пользователя
    }

    public void setId(Long id) {
        this.id = id;  // Устанавливает ID пользователя
    }

    public String getName() {
        return name;  // Возвращает имя пользователя
    }

    public void setName(String name) {
        this.name = name;  // Устанавливает имя пользователя
    }

    public String getEmail() {
        return email;  // Возвращает email пользователя
    }

    public void setEmail(String email) {
        this.email = email;  // Устанавливает email пользователя
    }

    public String getPassword() {
        return password;  // Возвращает пароль пользователя
    }

    public void setPassword(String password) {
        this.password = password;  // Устанавливает пароль пользователя
    }
}