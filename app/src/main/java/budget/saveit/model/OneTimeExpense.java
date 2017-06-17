package budget.saveit.model;

import java.util.Date;

import static budget.saveit.helper.DateHelper.sanitiseDate;

/**
 * Created by aa on 12/06/17.
 */

public class OneTimeExpense extends Expense {
    private Date date;
    private int amount;

    public OneTimeExpense(int amount, Date date) {

        if (amount == 0) {
            throw new NullPointerException("amount is 0 XD");
        }

        this.amount = amount;

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.date = sanitiseDate(date);
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }
}
