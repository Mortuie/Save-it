package budget.saveit.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import budget.saveit.helper.DateHelper;

import static budget.saveit.helper.DateHelper.getDayOfMonth;
import static budget.saveit.helper.DateHelper.sanitiseDate;

/**
 * Created by aa on 12/06/17.
 */

public class MonthlyExpense extends Expense {
    private int startAmount;
    private int dayOfMonth;
    private Date startDate;
    private Date endDate;
    private Map<Date, Integer> modifications = new HashMap<>();

    public MonthlyExpense(String title, int startAmount, Date startDate) {
        this(title, startAmount, startDate, null);
    }

    public MonthlyExpense(String title, int startAmount, Date startDate, Date endDate) {
        super(title);

        if (startAmount == 0) {
            throw new NullPointerException("startAmount is 0 XD");
        }

        this.startAmount = startAmount;

        if (startDate == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.startDate = sanitiseDate(startDate);
        this.dayOfMonth = DateHelper.getDayOfMonth(startDate);
        this.endDate = sanitiseDate(endDate);
    }

    public MonthlyExpense(String title, int startAmount, Date startDate, Date endDate, Map<Date, Integer> modifications) {
        this(title, startAmount, startDate, endDate);

        if (modifications == null) {
            throw new NullPointerException("Modifications is null XD");
        }

        this.modifications = modifications;
    }

    public int getAmountForMonth(Date date) {
        Date sanitisedDate = sanitiseDate(date);
        int amount = startAmount;

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

    // Help taken from Google's JSON tutorial
    public static String modificationsToJSON(MonthlyExpense expense) throws JSONException {
        JSONArray array = new JSONArray();

        for (Date modification : expense.modifications.keySet()) {
            Integer amount = expense.modifications.get(modification);

            JSONObject modJSON = new JSONObject();
            modJSON.put("d", modification.getTime());
            modJSON.put("a", amount);
            array.put(modJSON);
        }

        return array.toString();
    }

    public static Map<Date, Integer> jsonToModifications(String JSONString) throws JSONException {
        JSONArray array = new JSONArray(JSONString);

        Map<Date, Integer> modifications = new HashMap<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject modJSON = array.getJSONObject(i);
            modifications.put(new Date(modJSON.getInt("d")), modJSON.getInt("a"));
        }

        return modifications;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
