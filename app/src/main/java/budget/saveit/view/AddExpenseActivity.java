package budget.saveit.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import budget.saveit.R;

/**
 * Created by hampe on 19 June 2017.
 */

public class AddExpenseActivity extends AppCompatActivity {
    private boolean isRevenue = false;
    private EditText descriptionEditText;
    private EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setButtons();
        setTextFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (validateInput()) {
                finish();
            }
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setButtons() {
        final ImageView paymentCheckbox = (ImageView) findViewById(R.id.payment_checkbox_imageview);
        final ImageView revenueCheckbox = (ImageView) findViewById(R.id.revenue_checkbox_imageview);

        findViewById(R.id.payment_button_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRevenue) {
                    isRevenue = false;
                    paymentCheckbox.setImageResource(R.mipmap.ic_radio_button_on);
                    revenueCheckbox.setImageResource(R.mipmap.ic_radio_button_off);
                }
            }
        });

        findViewById(R.id.revenue_button_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRevenue) {
                    isRevenue = true;
                    paymentCheckbox.setImageResource(R.mipmap.ic_radio_button_off);
                    revenueCheckbox.setImageResource(R.mipmap.ic_radio_button_on);
                }
            }
        });
    }

    private void setTextFields() {
        final TextView description = (TextView) findViewById(R.id.description_descriptor);
        final TextView amount = (TextView) findViewById(R.id.amount_descriptor);

        descriptionEditText = (EditText) findViewById(R.id.description_edittext);
        descriptionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    description.setTextColor(getResources().getColor(R.color.accent));
                } else {
                    description.setTextColor(getResources().getColor(R.color.secondary_text));
                }
            }
        });

        amountEditText = (EditText) findViewById(R.id.amount_edittext);
        amountEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    amount.setTextColor(getResources().getColor(R.color.accent));
                } else {
                    amount.setTextColor(getResources().getColor(R.color.secondary_text));
                }
            }
        });
    }

    private boolean validateInput() {
        boolean ok = true;

        String description = descriptionEditText.getText().toString();
        if (description.trim().isEmpty()) {
            descriptionEditText.setError("Enter a description");
            ok = false;
        }

        String amount = amountEditText.getText().toString();

        try {
            int value = Integer.parseInt(amount);
            if (value <= 0) {
                amountEditText.setError("Amount should be greater than 0.");
                ok = false;
            }
        } catch (Exception e) {
            amountEditText.setError("Not a valid amount");
            ok = false;
        }

        return ok;
    }
}
