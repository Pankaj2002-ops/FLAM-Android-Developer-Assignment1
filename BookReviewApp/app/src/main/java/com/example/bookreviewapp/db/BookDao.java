// BookDao.java
package com.example.bookreviewapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.bookreviewapp.model.FavoriteBook;
import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insertFavorite(FavoriteBook favoriteBook);

    @Delete
    void deleteFavorite(FavoriteBook favoriteBook);

    @Query("SELECT * FROM favorite_book")
    LiveData<List<FavoriteBook>> getAllFavorites();

    @Query("SELECT * FROM favorite_book WHERE id = :id")
    FavoriteBook getFavoriteById(int id);
}
