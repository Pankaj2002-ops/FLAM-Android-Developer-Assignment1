// BookDatabase.java
package com.example.bookreviewapp.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.bookreviewapp.model.FavoriteBook;

@Database(entities = {FavoriteBook.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
    private static volatile BookDatabase INSTANCE;

    public static BookDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized(BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        BookDatabase.class,
                        "book_database")
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
