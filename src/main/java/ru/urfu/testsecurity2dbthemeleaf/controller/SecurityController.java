package ru.urfu.testsecurity2dbthemeleaf.controller;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.urfu.testsecurity2dbthemeleaf.dto.UserDto;
import ru.urfu.testsecurity2dbthemeleaf.enity.User;
import ru.urfu.testsecurity2dbthemeleaf.repository.UserRepository;
import ru.urfu.testsecurity2dbthemeleaf.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SecurityController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SecurityController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Отображение главной страницы
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // Отображение страницы входа
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Логика для выхода (перенаправляет на страницу входа)
    @GetMapping
    public String logout() {
        return "login";
    }

    // Отображение формы регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);  // Добавляем пустой объект для отображения формы
        return "register";
    }

    // Обработка регистрации пользователя
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        // Проверка на наличие уже зарегистрированного пользователя с таким email
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "На этот адрес электронной почты уже зарегистрирована учетная запись");
        }

        // Если есть ошибки валидации, возвращаем форму с ошибками
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        // Сохранение нового пользователя
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // Отображение списка всех пользователей
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        // Доступные роли для назначения пользователю
        List<String> availableRoles = Arrays.asList("ADMIN", "USER", "READ_ONLY");
        model.addAttribute("availableRoles", availableRoles);
        return "users";
    }

    // Изменение роли пользователя
    @PostMapping("/users/change-role")
    public String changeUserRole(@RequestParam String email, @RequestParam String role) {
        // Получаем пользователя по email
        User user = userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get() : null;

        // Очищаем текущие роли и добавляем новую
        Set<String> updatedRoles = new HashSet<>(user.getRoles());
        updatedRoles.clear();  // Очищаем старые роли
        updatedRoles.add(role); // Добавляем новую роль
        user.setRoles(updatedRoles);

        // Сохраняем обновленного пользователя в базе данных
        userRepository.save(user);

        return "redirect:/users";  // Перенаправление на страницу пользователей
    }
}
