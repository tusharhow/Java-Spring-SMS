package com.varsity.demo.demo.controllers;


import com.varsity.demo.demo.models.Class;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ClassController {

    private List<com.varsity.demo.demo.models.Class> classes = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping("/classes")
    public String listClasses(Model model) {
        model.addAttribute("classes", classes);
        return "classes/class-list";
    }

    @GetMapping("/classes/add")
    public String showAddForm(Model model) {
        model.addAttribute("class", new com.varsity.demo.demo.models.Class());
        return "classes/class-add";
    }

    @PostMapping("/classes/add")
    public String addClass(@ModelAttribute com.varsity.demo.demo.models.Class classObj) {
        classObj.setId(idCounter++);
        classes.add(classObj);
        return "redirect:/classes";
    }

    @GetMapping("/classes/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        com.varsity.demo.demo.models.Class classObj = findClassById(id);
        if (classObj != null) {
            model.addAttribute("class", classObj);
            return "classes/class-edit";
        }
        return "redirect:/classes";
    }

    @PutMapping("/classes/edit/{id}")
    public String updateClass(@PathVariable Long id, @ModelAttribute com.varsity.demo.demo.models.Class classObj) {
        int index = findClassIndex(id);
        if (index != -1) {
            classObj.setId(id);
            classes.set(index, classObj);
        }
        return "redirect:/classes";
    }

    @DeleteMapping("/classes/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        classes.removeIf(classObj -> classObj.getId().equals(id));
        return "redirect:/classes";
    }

    private Class findClassById(Long id) {
        return classes.stream()
                .filter(classObj -> classObj.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findClassIndex(Long id) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}