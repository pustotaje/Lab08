package ru.urfu.testsecurity2dbthemeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.testsecurity2dbthemeleaf.dto.UserDto;
import ru.urfu.testsecurity2dbthemeleaf.enity.User;
import ru.urfu.testsecurity2dbthemeleaf.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;  // Репозиторий для работы с пользователями
    private final PasswordEncoder passwordEncoder;  // Для безопасного кодирования паролей

    // Конструктор для внедрения зависимостей
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для сохранения пользователя
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        // Устанавливаем имя пользователя как объединение firstName и lastName
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));  // Кодируем пароль
        // Устанавливаем роли (по умолчанию роль "READ_ONLY")
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            user.setRoles(userDto.getRoles());
        } else {
            user.setRoles(new HashSet<>(Set.of("READ_ONLY")));
        }
        // Сохраняем пользователя в базе данных
        userRepository.save(user);
    }

    // Метод для поиска пользователя по email
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get() : null;
    }

    // Метод для получения списка всех пользователей и преобразования их в UserDto
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        // Преобразуем список сущностей пользователей в список DTO
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    // Метод для преобразования сущности User в UserDto
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        // Разделяем имя на первую и вторую часть
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        // Если фамилия есть, устанавливаем её
        if (str.length > 1) {
            userDto.setLastName(str[1]);
        } else {
            userDto.setLastName("");
        }
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());  // Устанавливаем роли пользователя
        return userDto;
    }
}