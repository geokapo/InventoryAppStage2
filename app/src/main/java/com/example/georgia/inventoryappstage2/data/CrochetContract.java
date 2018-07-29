package com.example.georgia.inventoryappstage2.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class CrochetContract {

    public static final String CONTENT_AUTHORITY = "com.example.georgia.inventoryappstage2";
    public static final Uri BASE_CONTENT_URI = Uri.parse ( "content://" + CONTENT_AUTHORITY );
    // The uri can go 2 paths later. This path is for the whole table crochet
    public static final String PATH_CROCHET = "crochet";


    public static class CrochetEntry implements BaseColumns {
        public static final String TABLE_NAME = "crochet";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CROCHET_NAME = "product_name";
        public static final String COLUMN_CROCHET_PRICE = "price";
        public static final String COLUMN_CROCHET_QUANTITY = "quantity";
        public static final String COLUMN_CROCHET_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_CROCHET_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        // this must be inside the CrochetEntry class Creates an Uri with BASE_CONTENT_URI, PATH_CROCHET.
        public static final Uri CONTENT_URI = Uri.withAppendedPath ( BASE_CONTENT_URI, PATH_CROCHET );

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of crochet.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CROCHET;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single crochet.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CROCHET;

    }
}
