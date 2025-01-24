package ru.urfu.testsecurity2dbthemeleaf.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity  // Указывает, что класс является сущностью JPA
@NoArgsConstructor  // Генерирует конструктор без параметров
@AllArgsConstructor  // Генерирует конструктор с параметрами
@Table(name = "PRESENTS")  // Указывает имя таблицы в базе данных
public class Present {

    @Id  // Указывает, что это основной ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автогенерация значения ID
    @Column(name = "id")  // Столбец для идентификатора в таблице
    private Long id;

    @Column(name = "name")  // Столбец для имени подарка
    private String name;

    @Column(name = "price")  // Столбец для цены подарка
    private Long price;

    @OneToMany(mappedBy = "present", cascade = CascadeType.ALL)  // Связь "один ко многим" с сущностью Employee
    private List<Employee> employees;  // Список сотрудников, которые получают этот подарок

    // Геттеры и сеттеры для всех полей

    public Long getId() {
        return id;  // Возвращает ID подарка
    }

    public void setId(Long id) {
        this.id = id;  // Устанавливает ID подарка
    }

    public String getName() {
        return name;  // Возвращает имя подарка
    }

    public void setName(String name) {
        this.name = name;  // Устанавливает имя подарка
    }

    public Long getPrice() {
        return price;  // Возвращает цену подарка
    }

    public void setPrice(Long price) {
        this.price = price;  // Устанавливает цену подарка
    }

    public List<Employee> getEmployees() {
        return employees;  // Возвращает список сотрудников, получающих подарок
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;  // Устанавливает список сотрудников, получающих подарок
    }
}
