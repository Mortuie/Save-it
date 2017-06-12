package budget.saveit.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aa on 12/06/17.
 */

public class Expense {
    private final int amount;

    public Expense(final int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Expense should be greater than 0!");
        }

        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isRevenue() {
        return amount < 0;
    }

    public static Date sanitiseDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
