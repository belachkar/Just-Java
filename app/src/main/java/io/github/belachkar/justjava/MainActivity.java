package io.github.belachkar.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * This method is called when the Order button is clicked
     */
    public void submitOrder(View view) {
        display(1);
    }

    /*
     * Display the given quantity on the screen
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_nbr);
        quantityTextView.setText(String.format(Locale.US, "%d", number));
    }
}
