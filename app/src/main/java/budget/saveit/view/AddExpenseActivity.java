package budget.saveit.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import budget.saveit.R;

/**
 * Created by hampe on 19 June 2017.
 */

public class AddExpenseActivity extends AppCompatActivity {
    private boolean isRevenue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setButtons();
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
            finish();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setButtons() {
        final ImageView paymentCheckboxImageView = (ImageView) findViewById(R.id.payment_checkbox_imageview);
        final ImageView revenueCheckboxImageView = (ImageView) findViewById(R.id.revenue_checkbox_imageview);

        findViewById(R.id.payment_button_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRevenue) {
                    isRevenue = false;
                    paymentCheckboxImageView.setImageResource(R.mipmap.ic_radio_button_on);
                    revenueCheckboxImageView.setImageResource(R.mipmap.ic_radio_button_off);
                }
            }
        });

        findViewById(R.id.revenue_button_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRevenue) {
                    isRevenue = true;
                    paymentCheckboxImageView.setImageResource(R.mipmap.ic_radio_button_off);
                    revenueCheckboxImageView.setImageResource(R.mipmap.ic_radio_button_on);
                }
            }
        });
    }
}
