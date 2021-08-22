package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;

    public int calculatePrice(boolean b1 , boolean b2){
        int baseprice=40;
        if (b1){
            baseprice+=10;
        }
        if (b2){
            baseprice+=15;
        }
        return (quantity*baseprice);
    }

    public void submitOrder(View view){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCreamCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolateCream = chocolateCreamCheckBox.isChecked();

        EditText name_Field = (EditText) findViewById(R.id.name_field);
        String name = name_Field.getText().toString();

        int price = calculatePrice(hasWhippedCream,hasChocolateCream);
        displayMessage(createOrderSummary(name,price,hasWhippedCream,hasChocolateCream));

        CheckBox email = (CheckBox) findViewById(R.id.email);
        boolean send = email.isChecked();
        if (send) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java App by " + name);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public String createOrderSummary(String name,int price,boolean c1,boolean c2){
        String priceMessage =getString(R.string.order_summary_name,name) ;
        priceMessage+="\nAdd whipped Cream : "+c1;
        priceMessage+="\nAdd Chocolate Cream : "+c2;
        priceMessage+= "\nQuantity : "+quantity ;
        priceMessage+="\nTotal : ₹"+ price ;
        priceMessage+="\n\""+getString(R.string.thank_you)+"\"";
        return priceMessage;

    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    public void increment(View view){
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this, "You Can't have less than 1 cup",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
        quantity=quantity-1;
        display(quantity);
        }
    }

    private void display(int n){
        TextView quantityTextView = (TextView) findViewById(
            R.id.quantity_text_view);
        quantityTextView.setText(""+n);

    }
    private void displayPrice(int n1){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(n1));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}