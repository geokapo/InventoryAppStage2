package com.example.georgia.inventoryappstage2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georgia.inventoryappstage2.data.CrochetContract.CrochetEntry;

public class CrochetCursorAdapter extends CursorAdapter {

    //this is a constructor
    public CrochetCursorAdapter(Context context, Cursor cursor) {
        super ( context, cursor, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from ( context ).inflate ( R.layout.list_item, parent, false );
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //Find field to populate in inflated in list_item.xml
        TextView crochetNameTextView = (TextView) view.findViewById ( R.id.product_crochet_name );
        TextView priceCrochetTextView = (TextView) view.findViewById ( R.id.price_crochet );
        TextView quantityCrochetTextView = (TextView) view.findViewById ( R.id.quantity );
        ImageButton sellButton = (ImageButton) view.findViewById ( R.id.sell_button );

        //Find the columns of crochet animal  that we are in interested in
        int nameColumnIndex = cursor.getColumnIndex ( CrochetEntry.COLUMN_CROCHET_NAME );
        int priceColumnIndex = cursor.getColumnIndex ( CrochetEntry.COLUMN_CROCHET_PRICE );
        int quantityColumnIndex = cursor.getColumnIndex ( CrochetEntry.COLUMN_CROCHET_QUANTITY );

        //Read the crochet animals from the Cursor for the current clothes
        String crochetName = "Name of Crochet: " + cursor.getString ( nameColumnIndex );
        String price = "Price: " + cursor.getString ( priceColumnIndex ) + " €";
        String quantity = "Quantity available: " + cursor.getString ( quantityColumnIndex );

        // sets the text on the Textviews
        crochetNameTextView.setText ( crochetName );
        priceCrochetTextView.setText ( price );
        quantityCrochetTextView.setText ( quantity );

        //Get the current quantity and make into an integer
        String currentQuantityString = cursor.getString ( quantityColumnIndex );
        final int currentQuantity = Integer.valueOf ( currentQuantityString );
        // Get the rows from the table with the ID
        final int productId = cursor.getInt ( cursor.getColumnIndex ( CrochetEntry._ID ) );

        //setting up the decrement on the sell button
        sellButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // setting up an if else statement to what will be done when the current quantity is > 0
                if (currentQuantity > 0) {
                    int newQuantity = currentQuantity - 1;

                    //Getting the URI with the append of the ID for the row
                    Uri quantityUri = ContentUris.withAppendedId ( CrochetEntry.CONTENT_URI, productId );

                    //Getting the current Value for quantity and updating them with the new value -1
                    ContentValues values = new ContentValues ();
                    values.put ( CrochetEntry.COLUMN_CROCHET_QUANTITY, newQuantity );
                    context.getContentResolver ().update ( quantityUri, values, null, null );
                }

                //Creating a Toast message that when the quantity is 0 this will be shown
                else {
                    Toast.makeText ( context, "This product is out of stock", Toast.LENGTH_SHORT ).show ();
                }
            }
        } );

    }

}
