package com.varsity.demo.demo.controllers;


import com.varsity.demo.demo.models.Mark;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class MarkController {

    private List<Mark> marks = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping("/marks")
    public String listMarks(Model model) {
        model.addAttribute("marks", marks);
        return "marks/mark-list";
    }

    @GetMapping("/marks/add")
    public String showAddForm(Model model) {
        model.addAttribute("mark", new Mark());
        return "marks/mark-add";
    }

    @PostMapping("/marks/add")
    public String addMark(@ModelAttribute Mark mark) {
        mark.setId(idCounter++);
        marks.add(mark);
        return "redirect:/marks";
    }

    @GetMapping("/marks/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Mark mark = findMarkById(id);
        if (mark != null) {
            model.addAttribute("mark", mark);
            return "marks/mark-edit";
        }
        return "redirect:/marks";
    }

    @PostMapping("/marks/edit/{id}")
    public String updateMark(@PathVariable Long id, @ModelAttribute Mark mark) {
        int index = findMarkIndex(id);
        if (index != -1) {
            mark.setId(id);
            marks.set(index, mark);
        }
        return "redirect:/marks";
    }

    @GetMapping("/marks/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marks.removeIf(mark -> mark.getId().equals(id));
        return "redirect:/marks";
    }

    private Mark findMarkById(Long id) {
        return marks.stream()
                .filter(mark -> mark.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findMarkIndex(Long id) {
        for (int i = 0; i < marks.size(); i++) {
            if (marks.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}