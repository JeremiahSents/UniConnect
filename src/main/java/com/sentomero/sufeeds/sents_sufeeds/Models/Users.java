package com.sentomero.sufeeds.sents_sufeeds.Models;

public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String course;
    private double yearOfStudy;
    private String username;
    private String password;

    public Users(int id, String firstName, String lastName, String dateOfBirth, double yearOfStudy, String course, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.yearOfStudy = yearOfStudy;
        this.course = course;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCourse() {
        return course;
    }

    public double getYearOfStudy() {
        return yearOfStudy;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYearOfStudy(double yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
