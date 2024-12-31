package com.varsity.demo.demo.controllers;

import com.varsity.demo.demo.models.Class;
import com.varsity.demo.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import com.varsity.demo.demo.services.LocalStorageService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;


@Controller
@RequestMapping("/students")
public class StudentController {

    private final LocalStorageService storageService;
    private List<Student> students;
    private Long idCounter = 1L;

    @Autowired
    public StudentController(LocalStorageService storageService) {
        this.storageService = storageService;
        this.students = storageService.loadStudents();
        if (!students.isEmpty()) {
            idCounter = students.stream()
                    .mapToLong(Student::getId)
                    .max()
                    .getAsLong() + 1;
        }
    }

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
        storageService.saveStudents(students);
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
            storageService.saveStudents(students);
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        students.removeIf(student -> student.getId().equals(id));
        storageService.saveStudents(students);
        return "redirect:/students";
    }

    @GetMapping("/{id}/assign-class")
    public String showAssignClassForm(@PathVariable Long id, Model model) {
        Student student = findStudentById(id);
        List<Class> availableClasses = storageService.loadClasses();
        model.addAttribute("student", student);
        model.addAttribute("availableClasses", availableClasses);
        return "students/assign-class";
    }

    @PostMapping("/{id}/assign-class")
    public String assignClass(@PathVariable Long id, @RequestParam String className) {
        Student student = findStudentById(id);
        if (student != null) {
            student.setClassName(className);
            int index = findStudentIndex(id);
            if (index != -1) {
                students.set(index, student);
                storageService.saveStudents(students);
            }
        }
        return "redirect:/students";
    }

    @GetMapping("/assign-classes")
    public String showAssignClassesForm(Model model) {
        List<Student> students = storageService.loadStudents();
        List<Class> classes = storageService.loadClasses();
        
        model.addAttribute("students", students);
        model.addAttribute("classes", classes);
        return "students/assign-classes";
    }

    @PostMapping("/assign-classes")
    public String assignClasses(@RequestParam Long studentId, @RequestParam String className) {
        Student student = findStudentById(studentId);
        if (student != null) {
            student.setClassName(className);
            int index = findStudentIndex(studentId);
            if (index != -1) {
                students.set(index, student);
                storageService.saveStudents(students);
            }
        }
        return "redirect:/students/assign-classes";
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