package ru.urfu.testsecurity2dbthemeleaf.service;

import org.springframework.beans.factory.annotation.Value;  // Аннотация для получения значений из конфигурации приложения
import org.springframework.boot.context.event.ApplicationReadyEvent;  // Событие, которое срабатывает после запуска приложения
import org.springframework.context.event.EventListener;  // Аннотация для обработки событий
import org.springframework.security.crypto.password.PasswordEncoder;  // Интерфейс для кодирования паролей
import org.springframework.stereotype.Component;  // Аннотация для обозначения компонента, управляемого Spring контейнером
import ru.urfu.testsecurity2dbthemeleaf.enity.User;  // Импортирует сущность User
import ru.urfu.testsecurity2dbthemeleaf.repository.UserRepository;  // Импортирует репозиторий для работы с User

import java.util.HashSet;
import java.util.Set;

@Component  // Компонент Spring, который автоматически будет зарегистрирован в контексте
public class AdminInitializer {

    private final UserRepository userRepository;  // Репозиторий для работы с сущностями User
    private final PasswordEncoder passwordEncoder;  // Для кодирования паролей

    // Значения для имени, пароля и почты администратора, которые могут быть переопределены в конфигурации приложения
    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:@dm1n}")
    private String adminPassword;

    @Value("${admin.email:admin@local}")
    private String adminEmail;

    // Конструктор с внедрением зависимостей
    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод, который срабатывает, когда приложение готово
    @EventListener(ApplicationReadyEvent.class)  // Обрабатывает событие готовности приложения
    public void createAdminIfNotExists() {
        // Проверка, существует ли уже администратор с данным email
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();  // Создаем нового пользователя
            admin.setName(adminUsername);  // Устанавливаем имя пользователя
            admin.setPassword(passwordEncoder.encode(adminPassword));  // Кодируем пароль
            admin.setEmail(adminEmail);  // Устанавливаем email
            admin.setRoles(new HashSet<>(Set.of("ADMIN")));  // Назначаем роль "ADMIN"
            userRepository.save(admin);  // Сохраняем администратора в базе данных
            System.out.println("Логин администратора: " + adminUsername);  // Выводим в консоль логин администратора
        }
    }
}
