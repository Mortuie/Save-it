package budget.saveit;

import android.app.Application;

import java.util.Date;

import budget.saveit.helper.Logger;
import budget.saveit.helper.ParameterKeys;
import budget.saveit.helper.Parameters;
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

       firstLaunchActions();
    }

    private void firstLaunchActions() {
        Logger.debug("First launch actions");
        long initDate = Parameters.getInstance(getApplicationContext()).getLong(ParameterKeys.INIT_DATE, 0);
        if (initDate <= 0) {
            Parameters.getInstance(getApplicationContext()).putLong(ParameterKeys.INIT_DATE, new Date().getTime());
        }
    }
}
