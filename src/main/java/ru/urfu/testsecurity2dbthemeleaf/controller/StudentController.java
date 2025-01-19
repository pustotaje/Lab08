package ru.urfu.testsecurity2dbthemeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.testsecurity2dbthemeleaf.enity.Student;
import ru.urfu.testsecurity2dbthemeleaf.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/list")
    public ModelAndView getAllStudents() {
        log.info("/list -> connection");
        ModelAndView modelAndView = new ModelAndView("list-students");
        modelAndView.addObject("students", studentRepository.findAll());
        return modelAndView;
    }
    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        ModelAndView modelAndView = new ModelAndView("add-student-form");
        Student student = new Student();
        modelAndView.addObject("student", student);
        return modelAndView;
    }
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/list";
    }
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId) {
        ModelAndView modelAndView = new ModelAndView("add-student-form");
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = new Student();
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
        }
        modelAndView.addObject("student", student);
        return modelAndView;
    }
    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        return "redirect:/list";
    }
}