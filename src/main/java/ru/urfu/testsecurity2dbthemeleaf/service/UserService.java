package ru.urfu.testsecurity2dbthemeleaf.service;

import ru.urfu.testsecurity2dbthemeleaf.dto.UserDto;
import ru.urfu.testsecurity2dbthemeleaf.enity.User;

import java.util.List;

public interface UserService {
    // Метод для сохранения пользователя, принимает UserDto для преобразования в сущность User
    void saveUser(UserDto userDto);

    // Метод для поиска пользователя по email, возвращает сущность User
    User findUserByEmail(String email);

    // Метод для получения всех пользователей в виде списка UserDto
    List<UserDto> findAllUsers();
}