package io.github.belachkar.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int qty = 2;
    private final int qtyUnits = 10;
    private final int coffeePrice = 5;
    private final int whippedCreamPrice = 1;
    private final int chocolatePrice = 2;

    private EditText nameEditText;
    private EditText emailEditText;
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
        emailEditText = findViewById(R.id.email_field);
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
    public void submitOrder(@NotNull View view) {
        boolean hasWhippedCream = hasWhippedCreamCheckBox.isChecked();
        boolean hasChocolate = hasChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String name = nameEditText.getText().toString();

        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        String email = emailEditText.getText().toString();
        String[] addresses = {email};
        String subject = getString(R.string.mail_subject);
        String body = getString(R.string.mail_test_header_notice) + "\n\n" + summary;

//        Create an intent to send an email with the order summary,
//        or display the order if not
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            displayMessage(summary);
        }
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
        int price = coffeePrice;
        price += hasChocolate ? chocolatePrice : 0;
        price += hasWhippedCream ? whippedCreamPrice : 0;
        return price * qty;
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
        String summary = getString(R.string.summary_name, name);
        summary += "\n" + getString(R.string.summary_add_whipped_cream, getResourceStringIdFromBool(hasWhippedCream));
        summary += "\n" + getString(R.string.summary_add_chocolate, getResourceStringIdFromBool(hasChocolate));
        summary += "\n" + getString(R.string.summary_quantity, qty);
        summary += "\n" + getString(R.string.summary_price, NumberFormat.getCurrencyInstance().format(price));
        summary += "\n\n" + getString(R.string.thank_you);
        return summary;
    }

    @NotNull
    private String getResourceStringIdFromBool(boolean hasIt) {
        if (hasIt) return getString(R.string._true);
        return getString(R.string._false);
    }


    public void increment(@NotNull View view) {
        if (qty > 99) {
            Toast.makeText(this, getString(R.string.msg_max_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        makeOrderSummaryUnfocused();
        qty += qtyUnits;
        if (qty > 99) qty = 100;
        displayQty();
    }

    public void decrement(@NotNull View view) {
        if (qty < 2) {
            Toast.makeText(this, getString(R.string.msg_min_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        makeOrderSummaryUnfocused();
        qty -= qtyUnits;
        if (qty < 2) qty = 1;
        displayQty();
    }

    private void displayMessage(String message) {
        orderSummaryTextView.setText(message);
        orderSummaryTextView.setTextColor(0xff111111);
    }

    private void makeOrderSummaryUnfocused() {
        orderSummaryTextView.setTextColor(0xff999999);
    }
}
