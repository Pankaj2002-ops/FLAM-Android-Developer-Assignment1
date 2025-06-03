// BookDetailViewModel.java
package com.example.bookreviewapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.model.FavoriteBook;
import com.example.bookreviewapp.repository.BookRepository;

public class BookDetailViewModel extends AndroidViewModel {
    private BookRepository repo;
    private MutableLiveData<Book> bookLive;

    public BookDetailViewModel(@NonNull Application application) {
        super(application);
        repo = new BookRepository(application);
        bookLive = new MutableLiveData<>();
    }

    // Load book details by ID
    public void loadBook(int id) {
        Book book = repo.getBookById(id);
        bookLive.setValue(book);
    }

    public LiveData<Book> getBook() {
        return bookLive;
    }

    // Expose toggleFavorite for detail screen
    public void toggleFavorite(Book book) {
        if (repo.isFavorite(book.getId())) {
            repo.removeFavorite(new FavoriteBook(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getRating()));
        } else {
            repo.addFavorite(new FavoriteBook(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getRating()));
        }
    }

    public boolean isFavorite(int bookId) {
        return repo.isFavorite(bookId);
    }
}
