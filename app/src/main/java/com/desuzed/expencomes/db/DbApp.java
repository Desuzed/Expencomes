package com.desuzed.expencomes.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.Item;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {Item.class, Category.class}, version = 1, exportSchema = false)
public abstract class DbApp extends RoomDatabase {
    public abstract ItemDAO itemDao();
    public abstract CategoryDAO categoryDao();

    private static volatile DbApp INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DbApp getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DbApp.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DbApp.class, Constants.DB_FILE_NAME)
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                Category c = new Category("TestCategory", MainActivity.TYPE_INCOME, 0);
                CategoryDAO cDao = INSTANCE.categoryDao();
                cDao.insertCategory(c);
            });
        }
    };
}
