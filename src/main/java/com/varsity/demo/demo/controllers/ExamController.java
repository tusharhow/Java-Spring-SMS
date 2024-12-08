package com.varsity.demo.demo.controllers;


import com.varsity.demo.demo.models.Exam;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ExamController {

    private List<Exam> exams = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping("/exams")
    public String listExams(Model model) {
        model.addAttribute("exams", exams);
        return "exams/exam-list";
    }

    @GetMapping("/exams/add")
    public String showAddForm(Model model) {
        model.addAttribute("exam", new Exam());
        return "exams/exam-add";
    }

    @PostMapping("/exams/add")
    public String addExam(@ModelAttribute Exam exam) {
        exam.setId(idCounter++);
        exams.add(exam);
        return "redirect:/exams";
    }

    @GetMapping("/exams/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Exam exam = findExamById(id);
        if (exam != null) {
            model.addAttribute("exam", exam);
            return "exams/exam-edit";
        }
        return "redirect:/exams";
    }

    @PostMapping("/exams/edit/{id}")
    public String updateExam(@PathVariable Long id, @ModelAttribute Exam exam) {
        int index = findExamIndex(id);
        if (index != -1) {
            exam.setId(id);
            exams.set(index, exam);
        }
        return "redirect:/exams";
    }

    @GetMapping("/exams/delete/{id}")
    public String deleteExam(@PathVariable Long id) {
        exams.removeIf(exam -> exam.getId().equals(id));
        return "redirect:/exams";
    }

    private Exam findExamById(Long id) {
        return exams.stream()
                .filter(exam -> exam.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int findExamIndex(Long id) {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}