package com.dev5151.educate.models;
import java.util.ArrayList;

public class Courses {

    public String courseId;
    public String name;
    public String description;
    public String teacher;
    public String assistant;
    public ArrayList<String> students;
    public ArrayList<String> quiz;
    public ArrayList<String> material;

    public ArrayList<String> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<String> videos) {
        this.videos = videos;
    }

    public ArrayList<String> videos;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getQuiz() {
        return quiz;
    }

    public void setQuiz(ArrayList<String> quiz) {
        this.quiz = quiz;
    }

    public ArrayList<String> getMaterial() {
        return material;
    }

    public void setMaterial(ArrayList<String> material) {
        this.material = material;
    }

    public Courses(String courseId, String name, String description, String teacher, String assistant, ArrayList<String> students, ArrayList<String> quiz, ArrayList<String> material, ArrayList<String> videos) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.assistant = assistant;
        this.students = students;
        this.quiz = quiz;
        this.material = material;
        this.videos = videos;
    }
}
