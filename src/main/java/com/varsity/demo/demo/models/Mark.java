package com.varsity.demo.demo.models;

public class Mark {
    private Long id;
    private String studentName;
    private String examName;
    private String subject;
    private String className;
    private Double marksObtained;
    private Double totalMarks;
    private String grade;
    private String remarks;

    public Mark() {}

    public Mark(Long id, String studentName, String examName, String subject, 
                String className, Double marksObtained, Double totalMarks, 
                String grade, String remarks) {
        this.id = id;
        this.studentName = studentName;
        this.examName = examName;
        this.subject = subject;
        this.className = className;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.grade = grade;
        this.remarks = remarks;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { this.marksObtained = marksObtained; }

    public Double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Double totalMarks) { this.totalMarks = totalMarks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}