package ru.urfu.testsecurity2dbthemeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.testsecurity2dbthemeleaf.enity.Employee;
import ru.urfu.testsecurity2dbthemeleaf.enity.Present;
import ru.urfu.testsecurity2dbthemeleaf.repository.PresentRepository;
import ru.urfu.testsecurity2dbthemeleaf.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PresentRepository presentRepository;

    // Отображение всех сотрудников
    @GetMapping("/list")
    public ModelAndView getAllEmployee(Authentication authentication) {
        log.info("/list -> connection");
        ModelAndView modelAndView = new ModelAndView("list-employees");

        // Получаем список всех сотрудников
        List<Employee> employees = employeeRepository.findAll();

        // Подсчитываем общую стоимость подарков сотрудников
        double totalPrice = employees.stream()
                .filter(employee -> employee.getPresent() != null)
                .mapToDouble(employee -> employee.getPresent().getPrice())
                .sum();

        modelAndView.addObject("employee", employees);
        modelAndView.addObject("totalPrice", totalPrice); // Добавляем в модель общую сумму

        // Определяем роли пользователя для отображения прав доступа
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean isReadOnly = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_READ_ONLY"));

        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isUser", isUser);
        modelAndView.addObject("isReadOnly", isReadOnly);

        return modelAndView;
    }

    // Отображение всех подарков
    @GetMapping("/list-presents")
    public ModelAndView getAllPresents(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("list-presents");
        modelAndView.addObject("presents", presentRepository.findAll());

        // Определяем роли пользователя для отображения прав доступа
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean isReadOnly = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_READ_ONLY"));

        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isUser", isUser);
        modelAndView.addObject("isReadOnly", isReadOnly);

        return modelAndView;
    }

    // Отображение формы для добавления подарка
    @GetMapping("/addPresentForm")
    public ModelAndView addPresentForm() {
        ModelAndView modelAndView = new ModelAndView("add-present-form");
        Present present = new Present();
        modelAndView.addObject("present", present);
        return modelAndView;
    }

    // Сохранение подарка
    @PostMapping("/savePresent")
    public String savePresent(@ModelAttribute Present present) {
        presentRepository.save(present);
        return "redirect:/list-presents";
    }

    // Удаление подарка
    @GetMapping("/deletePresent")
    public String deletePresent(@RequestParam Long id) {
        presentRepository.deleteById(id);
        return "redirect:/list-presents";
    }

    // Отображение формы для добавления сотрудника
    @GetMapping("/addEmployeeForm")
    public ModelAndView addEmployeeForm() {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        modelAndView.addObject("presents", presentRepository.findAll());
        Employee employee = new Employee();
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    // Сохранение сотрудника
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee, @RequestParam(required = false) Long presentId) {
        if (presentId != null) {
            Present present = presentRepository.findById(presentId).
                    orElseThrow(() -> new IllegalArgumentException("Invalid Present ID: " + presentId));
            employee.setPresent(present);
        }
        employeeRepository.save(employee);
        return "redirect:/list";
    }

    // Отображение формы для редактирования сотрудника
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long employeeId) {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = new Employee();
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }
        modelAndView.addObject("presents", presentRepository.findAll());
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    // Отображение формы для редактирования подарка
    @GetMapping("/showUpdatePresentForm")
    public ModelAndView showUpdatePresentForm(@RequestParam Long presentId) {
        ModelAndView modelAndView = new ModelAndView("add-present-form");
        Optional<Present> optionalPresent = presentRepository.findById(presentId);
        Present present = new Present();
        if (optionalPresent.isPresent()) {
            present = optionalPresent.get();
        }
        modelAndView.addObject("present", present);
        return modelAndView;
    }

    // Удаление сотрудника
    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeRepository.deleteById(employeeId);
        return "redirect:/list";
    }
}