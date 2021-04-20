package com.desuzed.expencomes.db;

public class Constants {
    public static final String DB_FILE_NAME = "expencomes.db";
    public static final String ITEMS_TABLE_NAME = "ITEMS_TABLE";
    public static final String _ID = "_ID";
    public static final String COLUMN_CATEGORY = "CATEGORY";
    public static final String COLUMN_VALUE = "VALUE";
    public static final String COLUMN_COMMENT = "COMMENT";
    public static final String COLUMN_TYPE = "TYPE";
    public static final int DB_VERSION = 1;
    public static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ITEMS_TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + COLUMN_CATEGORY + " TEXT," + COLUMN_VALUE + " INTEGER," + COLUMN_TYPE + " TEXT," +
            COLUMN_COMMENT + " TEXT)";
    public static final String DROP_ITEMS_TABLE = "DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME;
// ----------------------- CATEGORY----------------------------------------------------
    public static final String CATEGORY_TABLE_NAME = "CATEGORY_TABLE";
    public static final String CATEGORY_TOTAL_VALUE = "CATEGORY_TOTAL_VALUE";
    public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
            CATEGORY_TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + COLUMN_CATEGORY + " TEXT," + CATEGORY_TOTAL_VALUE + " INTEGER," + COLUMN_TYPE + " TEXT)";
    public static final String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;
}
