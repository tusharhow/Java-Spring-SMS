package com.varsity.demo.demo.controllers;


import com.varsity.demo.demo.models.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private List<Teacher> teachers = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping
    public ModelAndView listTeachers() {
        ModelAndView mv = new ModelAndView("teachers/teacher-list");
        mv.addObject("teachers", teachers);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView showAddForm() {
        ModelAndView mv = new ModelAndView("teachers/teacher-add");
        mv.addObject("teacher", new Teacher());
        return mv;
    }

    @PostMapping("/add")
    public String addTeacher(@ModelAttribute Teacher teacher) {
        teacher.setId(idCounter++);
        teachers.add(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Teacher teacher = findTeacherById(id);
        ModelAndView mv = new ModelAndView("teachers/teacher-edit");
        mv.addObject("teacher", teacher);
        return mv;
    }

    @PostMapping("/edit/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher) {
        int index = findTeacherIndex(id);
        if (index != -1) {
            teacher.setId(id);
            teachers.set(index, teacher);
        }
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teachers.removeIf(teacher -> teacher.getId().equals(id));
        return "redirect:/teachers";
    }

    private Teacher findTeacherById(Long id) {
        return teachers.stream()
                .filter(teacher -> teacher.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findTeacherIndex(Long id) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}