package budget.saveit;

import android.app.Application;

import java.util.Date;

import budget.saveit.model.Expense;
import budget.saveit.model.MonthlyExpense;
import budget.saveit.model.db.DB;

/**
 * Created by aa on 12/06/17.
 */

public class SaveIt extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DB db = new DB(getApplicationContext());
        db.clearDB();

        long monthlyID = db.addMonthlyExpense(new MonthlyExpense("Monthly", 10, new Date()));
        db.addExpense(new Expense("Monthly", 10, new Date(), monthlyID));

        db.addExpense(new Expense("Daily", 30, new Date()));
        db.addExpense(new Expense("Daily positive", -10, new Date()));
    }
}
