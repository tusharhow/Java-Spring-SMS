package com.varsity.demo.demo.controllers;

import com.varsity.demo.demo.models.Subject;
import com.varsity.demo.demo.models.Student;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.varsity.demo.demo.services.LocalStorageService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final LocalStorageService storageService;
    private List<Subject> subjects;
    private Map<Long, List<String>> subjectEnrollments;
    private Long idCounter = 1L;

    @Autowired
    public SubjectController(LocalStorageService storageService) {
        this.storageService = storageService;
        this.subjects = storageService.loadSubjects();
        this.subjectEnrollments = storageService.loadEnrollments();
        if (!subjects.isEmpty()) {
            idCounter = subjects.stream()
                    .mapToLong(Subject::getId)
                    .max()
                    .getAsLong() + 1;
        }
    }

    @GetMapping("")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjects);
        return "subjects/subject-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/subject-add";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subject.setId(idCounter++);
        subjects.add(subject);
        storageService.saveSubjects(subjects);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/enroll")
    public String showEnrollForm(@PathVariable Long id, Model model) {
        Subject subject = findSubjectById(id);
        List<Student> students = storageService.loadStudents();
        model.addAttribute("subject", subject);
        model.addAttribute("students", students);
        model.addAttribute("enrolledStudents", 
            subjectEnrollments.getOrDefault(id, new ArrayList<>()));
        return "subjects/enroll-student";
    }

    @PostMapping("/{subjectId}/enroll")
    public String enrollStudent(@PathVariable Long subjectId, @RequestParam String rollNumber) {
        if (!subjectEnrollments.containsKey(subjectId)) {
            subjectEnrollments.put(subjectId, new ArrayList<>());
        }
        subjectEnrollments.get(subjectId).add(rollNumber);
        storageService.saveEnrollments(subjectEnrollments);
        return "redirect:/subjects/" + subjectId + "/students";
    }
    
    @GetMapping("/{id}/students")
    public String viewEnrolledStudents(@PathVariable Long id, Model model) {
        Subject subject = findSubjectById(id);
        List<String> enrolledStudents = subjectEnrollments.getOrDefault(id, new ArrayList<>());
        model.addAttribute("subject", subject);
        model.addAttribute("enrolledStudents", enrolledStudents);
        return "subjects/enrolled-students";
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
            storageService.saveSubjects(subjects);
        }
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjects.removeIf(subject -> subject.getId().equals(id));
        subjectEnrollments.remove(id);
        storageService.saveSubjects(subjects);
        storageService.saveEnrollments(subjectEnrollments);
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