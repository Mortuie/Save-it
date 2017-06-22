package budget.saveit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import budget.saveit.R;
import budget.saveit.view.monthly.MonthlyRecyclerViewAdapter;

/**
 * Created by aa on 22/06/17.
 */

public class MonthlyExpensesManageActivity extends DBActivity {
    private static final int EDIT_MONTHLY_EXPENSE_ACTIVITY_CODE = 1000;

    private RecyclerView expensesRecyclerView;
    private MonthlyRecyclerViewAdapter expensesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_expenses_manage);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView(savedInstanceState);

        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monthly_expenses_manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        expensesRecyclerView = (RecyclerView) findViewById(R.id.monthlyExpensesRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.monthlyFab);
        fab.setColorRipple(getResources().getColor(R.color.accent));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(MonthlyExpensesManageActivity.this, MonthlyExpensesEditActivity.class);

                ActivityCompat.startActivityForResult(MonthlyExpensesManageActivity.this, startIntent, EDIT_MONTHLY_EXPENSE_ACTIVITY_CODE, null);
            }
        });

        expensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        expensesViewAdapter = new MonthlyRecyclerViewAdapter(this, db);
        expensesRecyclerView.setAdapter(expensesViewAdapter);
    }
}
