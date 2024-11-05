package com.sentomero.sufeeds.sents_sufeeds.Models;

public class User {  // Updated to "User" for a single instance representation
    private int id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String course;
    private String yearOfStudy;  // Changed from double to String
    private String username;
    private String password;

    // Constructor without ID, for cases where ID might be assigned by the database
    public User(String firstName, String lastName, String dateOfBirth, String course,
                String yearOfStudy, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
        this.yearOfStudy = yearOfStudy;
        this.username = username;
        this.password = password;
    }

    // Constructor with ID, for cases where ID is known (like updating a user)
    public User(int id, String firstName, String lastName, String dateOfBirth, String course,
                String yearOfStudy, String username, String password) {
        this(firstName, lastName, dateOfBirth, course, yearOfStudy, username, password);
        this.id = id;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getCourse() { return course; }
    public String getYearOfStudy() { return yearOfStudy; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setCourse(String course) { this.course = course; }
    public void setYearOfStudy(String yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
