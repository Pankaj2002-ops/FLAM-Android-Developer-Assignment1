// BookListActivity.java
package com.example.bookreviewapp.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapp.R;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.ui.adapter.BookListAdapter;
import com.example.bookreviewapp.viewmodel.BookListViewModel;

public class BookListActivity extends AppCompatActivity {
    private BookListViewModel viewModel;
    private BookListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new BookListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BookListViewModel.class);
        // Observe book list
        viewModel.getBooks().observe(this, books -> {
            adapter.setBooks(books);
        });
        // Observe favorites and update adapter
        viewModel.getFavoriteBooks().observe(this, favs -> {
            adapter.setFavoriteList(favs);
        });
        // Handle book item clicks
        adapter.setOnItemClickListener(book -> {
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        // Handle favorite button clicks
        adapter.setOnFavoriteClickListener(book -> {
            viewModel.toggleFavorite(book);
        });
    }
}
