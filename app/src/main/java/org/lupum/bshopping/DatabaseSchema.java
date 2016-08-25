package org.lupum.bshopping;

import android.provider.BaseColumns;

public final class DatabaseSchema {
    private DatabaseSchema() {
    }

    protected static final String SQL_CREATE_ENTRIES = String.format("" +
            "CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY, " +
            "%s TEXT, " +
            "%s INTEGER, " +
            ")",
            ProductEntry.TABLE_NAME,
            ProductEntry.COLUMN_NAME_PRODUCT_ID,
            ProductEntry.COLUMN_NAME_NAME,
            ProductEntry.COLUMN_NAME_SELECTED
    );

    protected static final String SQL_DELETE_ENTRIES = String.format("" +
            "DROP TABLE IF EXISTS %s",
            ProductEntry.TABLE_NAME
    );


    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SELECTED = "selected";
    }
}
