package com.example.georgia.inventoryappstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.georgia.inventoryappstage2.data.CrochetContract.CrochetEntry;

public class ProductDbHelper extends SQLiteOpenHelper {

    /**
     * Creates constants for the database name and database version
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "crochet.db";

    /**
     * Creates a constructor
     */

    public ProductDbHelper(Context context) {
        super ( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_CROCHET_TABLE = "CREATE TABLE " + CrochetEntry.TABLE_NAME + " ("
                + CrochetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CrochetEntry.COLUMN_CROCHET_NAME + " TEXT, "
                + CrochetEntry.COLUMN_CROCHET_PRICE + " INTEGER NOT NULL, "
                + CrochetEntry.COLUMN_CROCHET_QUANTITY + " INTEGER, "
                + CrochetEntry.COLUMN_CROCHET_SUPPLIER_NAME + " TEXT, "
                + CrochetEntry.COLUMN_CROCHET_SUPPLIER_PHONE_NUMBER + " LONG NOT NULL);";

        db.execSQL ( SQL_CREATE_CROCHET_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

