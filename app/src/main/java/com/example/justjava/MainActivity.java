package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text= (EditText) findViewById(R.id.name_field) ;
        String name = text.getText().toString();
        Log.v("MainActivity","Name: " + name);
        CheckBox whippedCreamCheckbox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox =(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        Log.v("MainActivity","has whipped cream"+ hasWhippedCream);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage =createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for: "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice = 5;
        if(addWhippedCream)
        {
            basePrice = basePrice+1;
        }
        if(addChocolate){
            basePrice =basePrice +2;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary( String name , int price,boolean addWhippedCream,boolean addChocolate)
    {
        String priceMessage ="Name: "+ name ;
        priceMessage+="\nadd Whipped cream: "+ addWhippedCream;
        priceMessage+="\nadd Chocolate: " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: "+ quantity;
        priceMessage = priceMessage +  "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /*
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}