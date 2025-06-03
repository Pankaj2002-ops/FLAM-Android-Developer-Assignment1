// BookListAdapter.java
package com.example.bookreviewapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookreviewapp.R;
import com.example.bookreviewapp.model.Book;
import com.example.bookreviewapp.model.FavoriteBook;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Set<Integer> favoriteIds = new HashSet<>();
    private OnItemClickListener itemClickListener;
    private OnFavoriteClickListener favClickListener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
    public interface OnFavoriteClickListener {
        void onFavoriteClick(Book book);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favClickListener = listener;
    }

    public void setBooks(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }
    public void setFavoriteList(List<FavoriteBook> favorites) {
        favoriteIds.clear();
        for (FavoriteBook fb : favorites) {
            favoriteIds.add(fb.getId());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textTitle.setText(book.getTitle());
        holder.textAuthor.setText(book.getAuthor());
        // Set favorite icon
        if (favoriteIds.contains(book.getId())) {
            holder.buttonFav.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            holder.buttonFav.setImageResource(android.R.drawable.btn_star_big_off);
        }
        // Item click (open detail)
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(book);
            }
        });
        // Favorite button click
        holder.buttonFav.setOnClickListener(v -> {
            if (favClickListener != null) {
                favClickListener.onFavoriteClick(book);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (bookList != null) ? bookList.size() : 0;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textAuthor;
        ImageButton buttonFav;
        BookViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            buttonFav = itemView.findViewById(R.id.button_favorite);
        }
    }
}
