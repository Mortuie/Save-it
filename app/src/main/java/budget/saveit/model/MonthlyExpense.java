package budget.saveit.model;

import java.util.Date;

import static budget.saveit.helper.DateHelper.sanitiseDate;

/**
 * Created by aa on 12/06/17.
 */

public class MonthlyExpense {
    private String title;
    private Date recurringDate;
    private int amount;
    private boolean modified = false;


    public MonthlyExpense(String title, int startAmount, Date recurringDate) {
        if (startAmount == 0) {
            throw new NullPointerException("startAmount is 0 XD");
        }
        this.amount = startAmount;

        if (title == null) {
            throw new IllegalArgumentException("Title is empty or null XD");
        }

        this.title = title;

        if (recurringDate == null) {
            throw new NullPointerException("recurringDate is null XD");
        }

        this.recurringDate = sanitiseDate(recurringDate);
    }

    public MonthlyExpense(String title, int startAmount, Date recurringDate, boolean modified) {
        this(title, startAmount, recurringDate);

        this.modified = modified;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isModified() {
        return modified;
    }

    public Date getRecurringDate() {
        return recurringDate;
    }
}
