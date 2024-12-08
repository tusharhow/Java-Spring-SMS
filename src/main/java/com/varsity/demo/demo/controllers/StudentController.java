package com.varsity.demo.demo.controllers;

import com.varsity.demo.demo.models.Student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping
    public ModelAndView listStudents() {
        ModelAndView mv = new ModelAndView("students/student-list");
        mv.addObject("students", students);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView showAddForm() {
        ModelAndView mv = new ModelAndView("students/student-add");
        mv.addObject("student", new Student());
        return mv;
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        student.setId(idCounter++);
        students.add(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Student student = findStudentById(id);
        ModelAndView mv = new ModelAndView("students/student-edit");
        mv.addObject("student", student);
        return mv;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        int index = findStudentIndex(id);
        if (index != -1) {
            student.setId(id);
            students.set(index, student);
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        students.removeIf(student -> student.getId().equals(id));
        return "redirect:/students";
    }

    private Student findStudentById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findStudentIndex(Long id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}