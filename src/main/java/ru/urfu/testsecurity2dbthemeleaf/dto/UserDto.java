package ru.urfu.testsecurity2dbthemeleaf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;  // Идентификатор пользователя

    @NotEmpty  // Проверка, чтобы имя не было пустым
    private String firstName;  // Имя пользователя

    @NotEmpty
    private String lastName;  // Фамилия пользователя

    @NotEmpty(message = "Email should not be empty")
    @Email  // Проверка корректности email
    private String email;  // Электронная почта пользователя

    @NotEmpty(message = "Password should not be empty")  // Сообщение об ошибке, если пароль пустой
    private String password;  // Пароль пользователя

    private Set<String> roles;  // Роли пользователя (например, ADMIN, USER)

    // Методы-геттеры и сеттеры для всех полей

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
