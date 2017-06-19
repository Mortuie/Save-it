package budget.saveit.view;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import budget.saveit.R;

/**
 * Created by hampe on 19 June 2017.
 */

public class AddExpenseActivity extends DBActivity {
    private boolean isRevenue = false;
    private EditText descriptionEditText;
    private EditText amountEditText;
    private Button dateButton;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date = (Date) getIntent().getSerializableExtra("date");

        setButtons();
        setTextFields();
        setDateButton();
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
                    description.setTypeface(null, Typeface.BOLD);
                } else {
                    description.setTextColor(getResources().getColor(R.color.secondary_text));
                    description.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        amountEditText = (EditText) findViewById(R.id.amount_edittext);
        amountEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    amount.setTextColor(getResources().getColor(R.color.accent));
                    amount.setTypeface(null, Typeface.BOLD);
                } else {
                    amount.setTextColor(getResources().getColor(R.color.secondary_text));
                    amount.setTypeface(null, Typeface.BOLD);
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

        if (amount.trim().isEmpty()) {
            amountEditText.setError("Enter an amount");
            ok = false;
        } else {
            try {
                int value = Integer.parseInt(amount);
                if (value <= 0) {
                    amountEditText.setError("Amount should be greater than 0");
                    ok = false;
                }
            } catch (Exception e) {
                amountEditText.setError("Not a valid amount");
                ok = false;
            }
        }

        return ok;
    }

    private void setDateButton() {
        dateButton = (Button) findViewById(R.id.date_button);
        updateDateButtonDisplay();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment fragment = new DatePickerDialogFragment(date, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();

                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        date = cal.getTime();
                        updateDateButtonDisplay();
                    }
                });
                fragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void updateDateButtonDisplay() {
        dateButton.setText(date.toString());
    }
}
