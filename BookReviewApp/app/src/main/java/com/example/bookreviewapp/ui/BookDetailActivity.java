// BookDetailActivity.java
package com.example.bookreviewapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookreviewapp.R;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.viewmodel.BookDetailViewModel;

public class BookDetailActivity extends AppCompatActivity {
    private BookDetailViewModel viewModel;
    private TextView titleView, authorView, descView;
    private RatingBar ratingBar;
    private Button favButton;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleView = findViewById(R.id.textTitleDetail);
        authorView = findViewById(R.id.textAuthorDetail);
        descView = findViewById(R.id.textDescription);
        ratingBar = findViewById(R.id.ratingBar);
        favButton = findViewById(R.id.buttonToggleFavorite);

        viewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);
        int bookId = getIntent().getIntExtra("book_id", -1);
        if (bookId != -1) {
            viewModel.loadBook(bookId);
            viewModel.getBook().observe(this, book -> {
                if (book != null) {
                    currentBook = book;
                    titleView.setText(book.getTitle());
                    authorView.setText(book.getAuthor());
                    descView.setText(book.getDescription());
                    ratingBar.setRating((float) book.getRating());

                    // Set initial favorite button text
                    if (viewModel.isFavorite(book.getId())) {
                        favButton.setText("Remove Favorite");
                    } else {
                        favButton.setText("Add to Favorites");
                    }
                }
            });
        }

        favButton.setOnClickListener(v -> {
            if (currentBook != null) {
                viewModel.toggleFavorite(currentBook);
                // Update button text
                if (viewModel.isFavorite(currentBook.getId())) {
                    favButton.setText("Add to Favorites");
                } else {
                    favButton.setText("Remove Favorite");
                }
            }
        });
    }
}
