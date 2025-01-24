package ru.urfu.testsecurity2dbthemeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // Метод для добавления контроллеров для представлений
    public void addViewControllers(ViewControllerRegistry registry) {

        // Контроллер для главной страницы
        registry.addViewController("/").setViewName("index");

        // Контроллер для страницы списка сотрудников
        registry.addViewController("/list").setViewName("list-employees");

        // Контроллер для страницы добавления нового сотрудника
        registry.addViewController("/addEmployeeForm").setViewName("add-employee-form");

        // Контроллер для страницы логина
        registry.addViewController("/login").setViewName("login");

        // Контроллер для страницы выхода (перенаправляет на страницу логина)
        registry.addViewController("/logout").setViewName("login");

        // Контроллер для страницы регистрации
        registry.addViewController("/register").setViewName("register");

        // Контроллер для страницы с пользователями
        registry.addViewController("/users").setViewName("users");

        // Контроллер для страницы добавления презента
        registry.addViewController("/addPresentForm").setViewName("add-present-form");

        // Контроллер для страницы списка презента
        registry.addViewController("/list-presents").setViewName("list-presents");

        // Контроллер для страницы "О нас"
        registry.addViewController("/about").setViewName("about");
    }
}
