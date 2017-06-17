package budget.saveit.view;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

import budget.saveit.R;
import budget.saveit.model.db.DB;
import budget.saveit.view.calendar.CalendarFragment;
import budget.saveit.view.expenses.ExpensesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private CalendarFragment calendarFragment;
    private RecyclerView expensesRecyclerView;
    private LinearLayoutManager expensesLayoutManager;
    private ExpensesRecyclerViewAdapter expensesViewAdapter;

    private DB db;
    private TextView budgetLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        budgetLine = (TextView) findViewById(R.id.budgetLine);
        db = new DB(getApplicationContext());

        initCalendarFragment();
        initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        calendarFragment = null;
        expensesLayoutManager = null;
        expensesRecyclerView = null;
        expensesViewAdapter = null;

        db.close();
        db = null;
        budgetLine = null;

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void initCalendarFragment() {
        calendarFragment = new CalendarFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
        args.putInt(CalendarFragment.START_DAY_OF_WEEK, CalendarFragment.MONDAY);

        calendarFragment.setArguments(args);
        calendarFragment.setSelectedDates(new Date(), new Date());

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                expensesViewAdapter = new ExpensesRecyclerViewAdapter(db, date);
                expensesRecyclerView.swapAdapter(expensesViewAdapter, true);

                calendarFragment.setSelectedDates(date, date);
                calendarFragment.refreshView();
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onCaldroidViewCreated() {
                Button leftButton = calendarFragment.getLeftArrowButton();
                Button rightButton = calendarFragment.getRightArrowButton();
                TextView textView = calendarFragment.getMonthTitleTextView();

                textView.setTextColor(MainActivity.this.getResources().getColor(R.color.primary_text));

                leftButton.setText("<");
                leftButton.setTextSize(25);
                leftButton.setGravity(Gravity.CENTER);
                leftButton.setTextColor(MainActivity.this.getResources().getColor(R.color.primary_light));
                leftButton.setBackgroundResource(R.drawable.calendar_month_switcher_button_drawable);

                rightButton.setText(">");
                rightButton.setTextSize(25);
                rightButton.setGravity(Gravity.CENTER);
                rightButton.setTextColor(MainActivity.this.getResources().getColor(R.color.primary_light));
                rightButton.setBackgroundResource(R.drawable.calendar_month_switcher_button_drawable);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    leftButton.setOutlineProvider(null);
                    rightButton.setOutlineProvider(null);
                }
            }
        };

        calendarFragment.setCaldroidListener(listener);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, calendarFragment);
        t.commit();
    }

    private void initRecyclerView() {
        expensesRecyclerView = (RecyclerView) findViewById(R.id.expensesRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(expensesRecyclerView);

        expensesLayoutManager = new LinearLayoutManager(this);
        expensesRecyclerView.setLayoutManager(expensesLayoutManager);

        expensesViewAdapter = new ExpensesRecyclerViewAdapter(db, new Date());
        expensesRecyclerView.setAdapter(expensesViewAdapter);

        budgetLine.setText("Account balance: £1234");
    }
}
