package budget.saveit.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import budget.saveit.helper.DateHelper;

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
