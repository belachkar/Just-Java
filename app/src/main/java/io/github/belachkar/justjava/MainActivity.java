package io.github.belachkar.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int qty = 2;
    private int qtyUnits = 1;
    private int unitPrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateView();
    }

    /*
     * This method is called when the Order button is clicked
     */
    public void submitOrder(View view) {
        updateView();
    }

    /*
     * Display the given quantity on the screen
     */
    private void displayQty() {
        TextView qtyTextView = findViewById(R.id.quantity_text_view);
        qtyTextView.setText(String.format(Locale.US, "%d", qty));
    }

    /*
     * Display the price
     */
    private void displayPrice() {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(qty * unitPrice));
    }

    /*
     * Increment the quantity
     */
    public void changeQty(View view) {
        int viewId = view.getId();
        int value = (viewId == R.id.add_btn) ? qtyUnits : -qtyUnits;
        qty += (qty + value) >= 0 ? value : 0;
        updateView();
    }

    /*
     * Update the values of Quantity and Price
     */
    private void updateView() {
        displayQty();
        displayPrice();
    }
}
