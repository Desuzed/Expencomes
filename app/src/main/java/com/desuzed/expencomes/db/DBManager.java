package com.desuzed.expencomes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desuzed.expencomes.model.BudgetItem;
import com.desuzed.expencomes.model.Category;

import java.util.ArrayList;

public class DBManager {
    private Context context;
    private DBAssistant dbAssistant;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        dbAssistant = new DBAssistant(context);
    }


    public void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_CATEGORY, category.getName());
        cv.put(Constants.COLUMN_TYPE, category.getType());
        db.insert(Constants.CATEGORY_TABLE_NAME, null, cv);
    }

    public void addItem(BudgetItem item) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_CATEGORY, item.getCategory());
        cv.put(Constants.COLUMN_VALUE, item.getValue());
        cv.put(Constants.COLUMN_COMMENT, item.getComment());
        cv.put(Constants.COLUMN_TYPE, item.getType());
        db.insert(Constants.ITEMS_TABLE_NAME, null, cv);
        updateCategoryTotalVal(item.getValue(), item.getCategory());
    }

    public ArrayList<Category> getCategoryList(String type) {
        ArrayList<Category> list = new ArrayList<>();
        String selection = Constants.COLUMN_TYPE + " =?";
        Cursor query = db.query(Constants.CATEGORY_TABLE_NAME, null, selection, new String[]{type}, null, null, Constants.COLUMN_CATEGORY + " ASC" );
        while (query.moveToNext()) {
            Category c = new Category(
                    query.getString(query.getColumnIndex(Constants.COLUMN_CATEGORY)),
                    query.getString(query.getColumnIndex(Constants.COLUMN_TYPE)),
                    query.getLong(query.getColumnIndex(Constants.CATEGORY_TOTAL_VALUE)));
            list.add(c);
        }
        return list;
    }

    public ArrayList<BudgetItem> getItemsByCategory(String categoryName) {
        ArrayList<BudgetItem> list = new ArrayList<>();
        String selection = Constants.COLUMN_CATEGORY + " =? ";
        Cursor query = db.query(Constants.ITEMS_TABLE_NAME, null, selection, new String[]{categoryName}, null, null, null);
        while (query.moveToNext()) {
            BudgetItem item = new BudgetItem(
                    query.getString(query.getColumnIndex(Constants.COLUMN_CATEGORY)),
                    query.getLong(query.getColumnIndex(Constants.COLUMN_VALUE)),
                    query.getString(query.getColumnIndex(Constants.COLUMN_COMMENT)),
                    query.getString(query.getColumnIndex(Constants.COLUMN_TYPE)));
            list.add(0, item);
        }
        return list;
    }

    public long getTotalBudgetValue(String type) {
        long total = 0;
        String selection = Constants.COLUMN_TYPE + " =?";
        Cursor query = db.query(Constants.CATEGORY_TABLE_NAME, null, selection, new String[]{type}, null, null, null);
        while (query.moveToNext()) {
            total = total + query.getLong(query.getColumnIndex(Constants.CATEGORY_TOTAL_VALUE));
        }
        return total;
    }

    public ArrayList<BudgetItem> getAllItemsList(String searchText) {
        ArrayList<BudgetItem> list = new ArrayList<>();
        String selection = Constants.COLUMN_CATEGORY + " like ?";
        Cursor query = db.query(Constants.ITEMS_TABLE_NAME, null, selection, new String[]{"%" + searchText + "%"}, null, null, null);
        while (query.moveToNext()) {
            BudgetItem item = new BudgetItem(
                    query.getString(query.getColumnIndex(Constants.COLUMN_CATEGORY)),
                    query.getLong(query.getColumnIndex(Constants.COLUMN_VALUE)),
                    query.getString(query.getColumnIndex(Constants.COLUMN_COMMENT)),
                    query.getString(query.getColumnIndex(Constants.COLUMN_TYPE)));
            list.add(item);
        }
        return list;
    }

    private void updateCategoryTotalVal(long newValue, String category) {
        ContentValues cv = new ContentValues();
        String selection = Constants.COLUMN_CATEGORY + " =? ";
        Cursor query = db.query(Constants.CATEGORY_TABLE_NAME, null, selection, new String[]{category}, null, null, null);
        long oldValue = 0;
        while (query.moveToNext()) {
            oldValue = query.getLong(query.getColumnIndex(Constants.CATEGORY_TOTAL_VALUE));
        }

        if (oldValue == 0) {
            cv.put(Constants.CATEGORY_TOTAL_VALUE, newValue);
        } else {
            long newVal = oldValue + newValue;
            cv.put(Constants.CATEGORY_TOTAL_VALUE, newVal);
        }
        db.update(Constants.CATEGORY_TABLE_NAME, cv, Constants.COLUMN_CATEGORY + "=?", new String[]{category});
    }

    public void openDB() {
        db = dbAssistant.getWritableDatabase();
    }

    public void closeDB() {
        db.close();
    }
}
