package budget.saveit.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aa on 12/06/17.
 */

public class MonthlyExpense extends Expense {
    private Date startDate;
    private Date endDate;
    private Map<Date, Integer> modifications = new HashMap<>();

    public MonthlyExpense(int startAmount, Date startDate, Date endDate) {
        super(startAmount);

        if (startDate == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.startDate = sanitiseDate(startDate);
        this.endDate = sanitiseDate(endDate);
    }

    public int getAmountForMonth(Date date) {
        Date sanitisedDate = sanitiseDate(date);
        int amount = getAmount();

        if (modifications.isEmpty()) {
            return amount;
        }

        for (Date d : modifications.keySet()) {
            if (d.before(sanitisedDate)) {
                amount = modifications.get(d);
            }
        }

        return amount;
    }

    private static Date sanitiseDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
