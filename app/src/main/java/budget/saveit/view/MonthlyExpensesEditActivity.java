package budget.saveit.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import budget.saveit.R;
import budget.saveit.model.MonthlyExpense;

/**
 * Created by aa on 22/06/17.
 */

public class MonthlyExpensesEditActivity extends DBActivity {
    private MonthlyExpense expense;
    private Date dateStart;
    private Date dateEnd;

// ------------------------------------------->

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_expense_edit);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateStart = (Date) getIntent().getSerializableExtra("dateStart");
        dateEnd = (Date) getIntent().getSerializableExtra("dateEnd");

        if (getIntent().hasExtra("expense")) {
            expense = (MonthlyExpense) getIntent().getSerializableExtra("expense");

            setTitle(R.string.title_activity_monthly_expense_edit);
        }

        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monthly_expense_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            //TODO
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}