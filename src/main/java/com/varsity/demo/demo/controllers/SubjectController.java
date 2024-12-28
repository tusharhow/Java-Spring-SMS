package com.varsity.demo.demo.controllers;


import com.varsity.demo.demo.models.Subject;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SubjectController {

    private List<Subject> subjects = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjects);
        return "subjects/subject-list";
    }

    @GetMapping("/subjects/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/subject-add";
    }

    @PostMapping("/subjects/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subject.setId(idCounter++);
        subjects.add(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subject subject = findSubjectById(id);
        if (subject != null) {
            model.addAttribute("subject", subject);
            return "subjects/subject-edit";
        }
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/edit/{id}")
    public String updateSubject(@PathVariable Long id, @ModelAttribute Subject subject) {
        int index = findSubjectIndex(id);
        if (index != -1) {
            subject.setId(id);
            subjects.set(index, subject);
        }
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjects.removeIf(subject -> subject.getId().equals(id));
        return "redirect:/subjects";
    }

    private Subject findSubjectById(Long id) {
        return subjects.stream()
                .filter(subject -> subject.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findSubjectIndex(Long id) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}