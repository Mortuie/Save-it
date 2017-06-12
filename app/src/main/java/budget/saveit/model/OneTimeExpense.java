package budget.saveit.model;

import java.util.Date;

/**
 * Created by aa on 12/06/17.
 */

public class OneTimeExpense extends Expense {
    private Date date;

    public OneTimeExpense(int amount, Date date) {
        super(amount);

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.date = sanitiseDate(date);
    }

    public Date getDate() {
        return date;
    }
}
