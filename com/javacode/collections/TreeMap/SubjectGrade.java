package com.javacode.collections.TreeMap;

import java.io.Serializable;
import java.util.Objects;
// этот класс хранит название предмета и оценку по нему
public class SubjectGrade implements Serializable {
    private final String subject;
    private final int grade;

    public SubjectGrade(String subject, int grade) {
        this.subject = subject;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectGrade that = (SubjectGrade) o;
        return grade == that.grade && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, grade);
    }

    @Override
    public String toString() {
        return "subject='" + subject + '\'' +
                ", grade=" + grade;
    }
}
