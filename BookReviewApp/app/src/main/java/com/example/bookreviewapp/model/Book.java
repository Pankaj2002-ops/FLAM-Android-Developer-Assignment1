// Book.java
package com.example.bookreviewapp.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String description;
    private double rating;

    // Constructor
    public Book(int id, String title, String author, String description, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }
    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
}
