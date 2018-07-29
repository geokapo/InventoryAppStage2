package com.example.georgia.inventoryappstage2;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.georgia.inventoryappstage2.data.CrochetContract.CrochetEntry;

public class InventoryActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the crochet data loader
     */
    private static final int CROCHET_LOADER = 0;

    /**
     * Adapter for the ListView
     */
    CrochetCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        //Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( InventoryActivity.this, EditorActivity.class );
                startActivity ( intent );
            }
        } );

        //Find the ListView which will be populated with the crochet animals data
        ListView crochetListView = (ListView) findViewById ( R.id.list_crochet_animals );


        //Find an set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById ( R.id.empty_view );
        crochetListView.setEmptyView ( emptyView );

        //sets the CursorAdapter on the Listview to create a list item for each row of the crochet data in the Cursor
        //There is no crochet data yet(until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new CrochetCursorAdapter ( this, null );
        crochetListView.setAdapter ( mCursorAdapter );

        crochetListView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new Intent to go to {@link EditorActivity}
                Intent intent = new Intent ( InventoryActivity.this, EditorActivity.class );

                //Form the content URI that represents the specific crochet that was clicked on,
                //by appending the "id" (passes as input to this method) onto the
                //{@link CrochetEntry#CONTENT_URI}.
                // For example, the URI would bee "content://com.example.android.inventoryappstage2/crochet/3"
                // if the crochet with ID 3 was clicked on.
                Uri currentCrochetUri = ContentUris.withAppendedId ( CrochetEntry.CONTENT_URI, id );

                intent.setData ( currentCrochetUri );

                startActivity ( intent );

            }
        } );

        getSupportLoaderManager ().initLoader ( CROCHET_LOADER, null, this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater ().inflate ( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId ()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.insert_dummy_data:
                insertCrochet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.delete_all_entries:
                deleteAllCrochet();
                return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    private void insertCrochet() {

        ContentValues values = new ContentValues ();

        values.put ( CrochetEntry.COLUMN_CROCHET_NAME, "Lion" );
        values.put ( CrochetEntry.COLUMN_CROCHET_PRICE, 25 );
        values.put ( CrochetEntry.COLUMN_CROCHET_QUANTITY, 10 );
        values.put ( CrochetEntry.COLUMN_CROCHET_SUPPLIER_NAME, "Stavroula" );
        values.put ( CrochetEntry.COLUMN_CROCHET_SUPPLIER_PHONE_NUMBER, 697364829 );

        Uri newUri = getContentResolver ().insert ( CrochetEntry.CONTENT_URI, values );

    }

    /**
     * Helper method to delete all crochet in the database.
     */
    private void deleteAllCrochet() {
        int rowsDeleted = getContentResolver ().delete ( CrochetEntry.CONTENT_URI, null, null );
        Log.v ( "InventoryActivity", rowsDeleted + " rows deleted from crochet database" );
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                CrochetEntry._ID,
                CrochetEntry.COLUMN_CROCHET_NAME,
                CrochetEntry.COLUMN_CROCHET_PRICE,
                CrochetEntry.COLUMN_CROCHET_QUANTITY,
        };

        return new CursorLoader ( this,
                CrochetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursorAdapter.swapCursor ( data );

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor ( null );

    }
}