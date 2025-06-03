// BookRepository.java
package com.example.bookreviewapp.repository;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import androidx.lifecycle.LiveData;

import com.example.bookreviewapp.db.BookDatabase;
import com.example.bookreviewapp.db.BookDao;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.model.FavoriteBook;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private BookDao bookDao;
    private LiveData<List<FavoriteBook>> favoriteBooks;
    private Context context;

    public BookRepository(Application application) {
        BookDatabase db = BookDatabase.getInstance(application);
        bookDao = db.bookDao();
        favoriteBooks = bookDao.getAllFavorites();
        context = application;
    }

    // Load all books from books.json in assets
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("books.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String title = obj.getString("title");
                String author = obj.getString("author");
                String description = obj.getString("description");
                double rating = obj.getDouble("rating");
                bookList.add(new Book(id, title, author, description, rating));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // Retrieve favorites LiveData
    public LiveData<List<FavoriteBook>> getAllFavoriteBooks() {
        return favoriteBooks;
    }

    // Add a book to favorites (Room insert)
    public void addFavorite(FavoriteBook favoriteBook) {
        new Thread(() -> bookDao.insertFavorite(favoriteBook)).start();
    }

    // Remove a book from favorites
    public void removeFavorite(FavoriteBook favoriteBook) {
        new Thread(() -> bookDao.deleteFavorite(favoriteBook)).start();
    }

    // Check if a book is already marked favorite
    public boolean isFavorite(int bookId) {
        FavoriteBook fb = bookDao.getFavoriteById(bookId);
        return fb != null;
    }

    // Find a single book by ID (from JSON list)
    public Book getBookById(int bookId) {
        for (Book book : getAllBooks()) {
            if (book.getId() == bookId) return book;
        }
        return null;
    }
}
