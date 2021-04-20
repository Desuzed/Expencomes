package com.desuzed.expencomes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAssistant extends SQLiteOpenHelper {
    public DBAssistant(@Nullable Context context) {
        super(context, Constants.DB_FILE_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.CREATE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(Constants.CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Constants.DROP_ITEMS_TABLE);
        sqLiteDatabase.execSQL(Constants.DROP_CATEGORY_TABLE);
        onCreate(sqLiteDatabase);
    }
}
