// BookListViewModel.java
package com.example.bookreviewapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.model.FavoriteBook;
import com.example.bookreviewapp.repository.BookRepository;
import java.util.List;

public class BookListViewModel extends AndroidViewModel {
    private BookRepository repo;
    private MutableLiveData<List<Book>> booksLive;
    private LiveData<List<FavoriteBook>> favoriteBooks;

    public BookListViewModel(@NonNull Application application) {
        super(application);
        repo = new BookRepository(application);
        booksLive = new MutableLiveData<>();
        loadBooks();
        favoriteBooks = repo.getAllFavoriteBooks();
    }

    // Load books from JSON and post to LiveData
    private void loadBooks() {
        List<Book> books = repo.getAllBooks();
        booksLive.setValue(books);
    }

    public LiveData<List<Book>> getBooks() {
        return booksLive;
    }
    public LiveData<List<FavoriteBook>> getFavoriteBooks() {
        return favoriteBooks;
    }

    // Toggle favorite: add or remove from Room
    public void toggleFavorite(Book book) {
        if (repo.isFavorite(book.getId())) {
            repo.removeFavorite(new FavoriteBook(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getRating()));
        } else {
            repo.addFavorite(new FavoriteBook(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getRating()));
        }
    }
}
