package budget.saveit;

import android.app.Application;

import java.util.Date;

import budget.saveit.model.MonthlyExpense;
import budget.saveit.model.OneTimeExpense;
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

        db.addOneTimeExpense(new OneTimeExpense("One Time", 30, new Date()));
        db.addMonthlyExpense(new MonthlyExpense("Monthly", 10, new Date()));
    }
}
