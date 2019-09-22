package io.github.belachkar.justjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String name = "Belachkar Ali";
    private Boolean hasWhippedCream = false;
    private int qty = 2;
    private int qtyUnits = 1;
    private int unitPrice = 5;

    private TextView orderSummaryTextView;
    private TextView qtyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Views pointers
        orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        qtyTextView = findViewById(R.id.quantity_text_view);

        // Display initial Quantity value
        displayQty();
    }

    /**
     * This method is called when the Order button is clicked
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        Log.v("MainActivity", "The price is " + price);

        String summary = createOrderSummary(price);
        displayMessage(summary);
    }

    /**
     * Display the given quantity on the screen
     */
    private void displayQty() {
        qtyTextView.setText(String.format(Locale.US, "%d", qty));
    }

    /**
     * Calculate the price
     *
     * @return total price
     */
    @org.jetbrains.annotations.Contract(pure = true)
    private int calculatePrice() {
        return qty * unitPrice;
    }

    /**
     * Create summary of the order
     *
     * @param totalPrice of the prder
     * @return text summary
     */
    private String createOrderSummary(int totalPrice) {
        String summary = "Name:\t\t\t\t\t" + name;
        summary += "\n" + "Add whipped cream? " + hasWhippedCream;
        summary += "\n" + "Quantity:\t\t\t" + qty;
        summary += "\n" + "Total Price:\t\t" + NumberFormat.getCurrencyInstance().format(totalPrice);
        summary += "\n\n" + "Thank you!";
        return summary;
    }

    /**
     * Change the quantity (Increment - Decrement)
     */
    public void changeQty(@NotNull View view) {
        int viewId = view.getId();
        int value = (viewId == R.id.add_btn) ? qtyUnits : -qtyUnits;
        qty += (qty + value) >= 0 ? value : 0;
        displayQty();
        makeOrderSummaryUnfocused();
    }

    private void displayMessage(String message) {
        orderSummaryTextView.setText(message);
        orderSummaryTextView.setTextColor(0xff111111);
    }

    private void makeOrderSummaryUnfocused() {
        orderSummaryTextView.setTextColor(0xff999999);
    }

    public void onWhippedCreamClicked(View view) {
        Log.v("MainActivity", "The whipped cream check box is clicked: " + hasWhippedCream);
        hasWhippedCream = ((CheckBox) view).isChecked();
    }


}
