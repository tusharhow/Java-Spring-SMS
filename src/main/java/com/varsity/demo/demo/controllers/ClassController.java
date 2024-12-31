package com.varsity.demo.demo.controllers;

import com.varsity.demo.demo.models.Class;
import com.varsity.demo.demo.services.LocalStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClassController {

    private final LocalStorageService storageService;
    private List<Class> classes;
    private Long idCounter = 1L;

    @Autowired
    public ClassController(LocalStorageService storageService) {
        this.storageService = storageService;
        this.classes = storageService.loadClasses();
        if (!classes.isEmpty()) {
            idCounter = classes.stream()
                    .mapToLong(Class::getId)
                    .max()
                    .getAsLong() + 1;
        }
    }

    @GetMapping("")
    public String listClasses(Model model) {
        model.addAttribute("classes", classes);
        return "classes/class-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("class", new Class());
        return "classes/class-add";
    }

    @PostMapping("/add")
    public String addClass(@ModelAttribute Class classObj) {
        classObj.setId(idCounter++);
        classes.add(classObj);
        storageService.saveClasses(classes);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Class classObj = findClassById(id);
        if (classObj != null) {
            model.addAttribute("class", classObj);
            return "classes/class-edit";
        }
        return "redirect:/classes";
    }

    @PutMapping("/edit/{id}")
    public String updateClass(@PathVariable Long id, @ModelAttribute Class classObj) {
        int index = findClassIndex(id);
        if (index != -1) {
            classObj.setId(id);
            classes.set(index, classObj);
        }
        return "redirect:/classes";
    }

    @DeleteMapping("/delete/{id}")
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