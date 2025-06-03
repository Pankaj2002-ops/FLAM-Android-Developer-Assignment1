// FavoriteBook.java (Room Entity)
package com.example.bookreviewapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_book")
public class FavoriteBook {
    @PrimaryKey
    private int id;
    private String title;
    private String author;
    private String description;
    private double rating;

    public FavoriteBook(int id, String title, String author, String description, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }
    // Getters and setters (Room requires getter/setter or public fields)
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
}
