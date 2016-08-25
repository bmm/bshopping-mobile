package org.lupum.bshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static Database sInstance;

    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bshopping.db";

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSchema.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseSchema.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Product addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseSchema.ProductEntry.COLUMN_NAME_NAME, product.getName());
            values.put(DatabaseSchema.ProductEntry.COLUMN_NAME_SELECTED, product.isSelected());

            long id = db.insertOrThrow(DatabaseSchema.ProductEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            product.setId(id);
        } catch (Exception e) {
            Log.d(Database.class.getName(), "Error while trying to add product to database");
        } finally {
            db.endTransaction();
        }

        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String PRODUCTS_SELECT_QUERY = String.format("" +
                " SELECT *" +
                " FROM %s" +
                " ORDER BY %s",
                DatabaseSchema.ProductEntry.TABLE_NAME,
                DatabaseSchema.ProductEntry.COLUMN_NAME_NAME
        );

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PRODUCTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getLong(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_PRODUCT_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_NAME)));
                    int selected = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_SELECTED));
                    product.setSelected(selected == 1);
                    products.add(product);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(Database.class.getName(), "Error while trying to get products from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return products;
    }

    public Product getProduct(long id) {
        Product product = new Product();

        String PRODUCT_SELECT_QUERY = String.format(
                "SELECT * FROM %s WHERE %s = %s" ,
                DatabaseSchema.ProductEntry.TABLE_NAME,
                DatabaseSchema.ProductEntry.COLUMN_NAME_PRODUCT_ID,
                id
        );

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PRODUCT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    product.setId(cursor.getLong(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_PRODUCT_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_NAME)));
                    int selected = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.ProductEntry.COLUMN_NAME_SELECTED));
                    product.setSelected(selected == 1);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(Database.class.getName(), "Error while trying to get product from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return product;
    }

    public Product updateProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ProductEntry.COLUMN_NAME_NAME, p.getName());
        values.put(DatabaseSchema.ProductEntry.COLUMN_NAME_SELECTED, p.isSelected());

        db.update(
                DatabaseSchema.ProductEntry.TABLE_NAME,
                values,
                DatabaseSchema.ProductEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(p.getId()) });
        return p;
    }

    public Long deleteProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                DatabaseSchema.ProductEntry.TABLE_NAME,
                String.format("%s = %s", DatabaseSchema.ProductEntry.COLUMN_NAME_PRODUCT_ID, p.getId()),
                null
        );
        return p.getId();
    }
}
