package budget.saveit.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public MonthlyExpense(int startAmount, Date startDate, Date endDate, Map<Date, Integer> modifications) {
        this(startAmount, startDate, endDate);

        if (modifications == null) {
            throw new NullPointerException("Modifications is null XD");
        }

        this.modifications = modifications;
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
}
