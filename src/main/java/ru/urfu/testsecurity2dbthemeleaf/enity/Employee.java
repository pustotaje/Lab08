package ru.urfu.testsecurity2dbthemeleaf.enity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity  // Указывает, что класс является сущностью JPA
@Table(name = "EMPLOYEE")  // Указывает имя таблицы в базе данных
public class Employee {

    @Id  // Указывает, что это основной ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автогенерация значения ID
    @Column(name = "id")  // Указывает имя столбца для идентификатора в таблице
    private Long id;

    @Column(name = "name")  // Столбец для имени сотрудника
    private String name;

    @Column(name = "surname")  // Столбец для фамилии сотрудника
    private String surname;

    @Column(name = "position")  // Столбец для должности сотрудника
    private String position;

    @Column(name = "age")  // Столбец для возраста сотрудника
    private int age;

    @Column(name = "birthday")  // Столбец для даты рождения сотрудника
    private LocalDate birthday;

    @ManyToOne  // Связь "многие к одному" с сущностью Present (подарок)
    @JoinColumn(name = "present_id")  // Столбец для связи с подарком сотрудника
    private Present present;

    // Геттеры и сеттеры для всех полей

    public Present getPresent() {
        return present;  // Возвращает подарок сотрудника
    }

    public void setPresent(Present present) {
        this.present = present;  // Устанавливает подарок сотрудника
    }

    public LocalDate getBirthday() {
        return birthday;  // Возвращает дату рождения сотрудника
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;  // Устанавливает дату рождения сотрудника
    }

    public Long getId() {
        return id;  // Возвращает ID сотрудника
    }

    public void setId(Long id) {
        this.id = id;  // Устанавливает ID сотрудника
    }

    public String getName() {
        return name;  // Возвращает имя сотрудника
    }

    public void setName(String name) {
        this.name = name;  // Устанавливает имя сотрудника
    }

    public String getSurname() {
        return surname;  // Возвращает фамилию сотрудника
    }

    public void setSurname(String surname) {
        this.surname = surname;  // Устанавливает фамилию сотрудника
    }

    public String getPosition() {
        return position;  // Возвращает должность сотрудника
    }

    public void setPosition(String position) {
        this.position = position;  // Устанавливает должность сотрудника
    }

    public int getAge() {
        return age;  // Возвращает возраст сотрудника
    }

    public void setAge(int age) {
        this.age = age;  // Устанавливает возраст сотрудника
    }
}