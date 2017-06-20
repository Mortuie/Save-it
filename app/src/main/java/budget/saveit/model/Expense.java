package budget.saveit.model;

import java.io.Serializable;
import java.util.Date;

import budget.saveit.helper.DateHelper;

/**
 * Created by aa on 17/06/17.
 */

public class Expense implements Serializable {
    private Long id;
    private String title;
    private int amount;
    private Date date;
    private Long monthlyID;

    public Expense(String title, int amount, Date date) {
        this(null, title, amount, date, null);
    }

    public Expense(String title, int amount, Date date, Long monthlyID) {
        this(null, title, amount, date, monthlyID);
    }

    public Expense(Long id, String title, int amount, Date date, Long monthlyID) {
        this.id = id;

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is empty or null XD");
        }

        this.title = title;

        if (amount == 0) {
            throw new IllegalArgumentException("Amount should be greater than 0");
        }

        this.amount = amount;

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.date = DateHelper.sanitiseDate(date);
        this.monthlyID = monthlyID;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getMonthlyID() {
        return monthlyID;
    }

    public void setMonthlyID(Long monthlyID) {
        this.monthlyID = monthlyID;
    }

    public boolean isMonthly() {
        return monthlyID != null;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }
}
