package io.github.belachkar.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int qty = 2;
    private int qtyUnits = 1;
    private int coffeePrice = 5;
    private int whippedCreamPrice = 1;
    private int chocolatePrice = 2;

    private EditText nameEditText;
    private TextView orderSummaryTextView;
    private TextView qtyTextView;
    private CheckBox hasWhippedCreamCheckBox;
    private CheckBox hasChocolateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Views pointers
        nameEditText = findViewById(R.id.name_field);
        orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        qtyTextView = findViewById(R.id.quantity_text_view);
        hasWhippedCreamCheckBox = findViewById(R.id.whippedCreamCheckBox);
        hasChocolateCheckBox = findViewById(R.id.chocolateCheckBox);

        // Initialize parameter values
        orderSummaryTextView.setText("");
        qtyTextView.setText(String.format(Locale.US, "%d", qty));
        hasWhippedCreamCheckBox.setChecked(false);
        hasChocolateCheckBox.setChecked(false);

        // Display initial Quantity value
        displayQty();
    }

    /**
     * This method is called when the Order button is clicked
     */
    public void submitOrder(View view) {
        boolean hasWhippedCream = hasWhippedCreamCheckBox.isChecked();
        boolean hasChocolate = hasChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String name = nameEditText.getText().toString();

        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
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
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 0;
        if (qty > 0) {
            price += coffeePrice;
            price += hasChocolate ? chocolatePrice : 0;
            price += hasWhippedCream ? whippedCreamPrice : 0;
            price *= qty;
        }
        return price;
    }

    /**
     * Create summary of the order
     *
     * @param price           of the order.
     * @param hasWhippedCream is whether or not the user wants Whipped cream.
     * @param hasChocolate    is whether or noe the user wants Chocolate.
     * @param name            The name of the user.
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String summary = "Name:\t\t\t\t\t" + name;
        summary += "\n" + "Add whipped cream? " + hasWhippedCream;
        summary += "\n" + "Add chocolate? " + hasChocolate;
        summary += "\n" + "Quantity:\t\t\t" + qty;
        summary += "\n" + "Total Price:\t\t" + NumberFormat.getCurrencyInstance().format(price);
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

}
