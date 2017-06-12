package budget.saveit.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import budget.saveit.helper.Logger;
import budget.saveit.model.Expense;
import budget.saveit.model.MonthlyExpense;
import budget.saveit.model.OneTimeExpense;

/**
 * Created by aa on 12/06/17.
 */

public final class DB {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteDBHelper helper;

    public DB(Context context) throws SQLException {
        if (context == null) {
            throw new NullPointerException("Context is null XD");
        }

        this.context = context.getApplicationContext();
        helper = new SQLiteDBHelper(this.context);
        database = helper.getWritableDatabase();
    }

    public void close() {
        try {
            database.close();
            helper = null;
        } catch (Exception e) {
            Logger.error("Error while closing SQLiteDatabase", e);
        }
    }

    public List<OneTimeExpense> getOneTimeExpensesForDay(Date date) {
        date = Expense.sanitiseDate(date);

        Cursor cursor = null;
        try {
            List<OneTimeExpense> expenses = new ArrayList<>();

            cursor = database.query(SQLiteDBHelper.TABLE_ONE_TIME_EXPENSE, null,
                    SQLiteDBHelper.COLUMN_ONE_TIME_DATE + " = " + date.getTime(),
                    null, null, null, null, null);

            while (cursor.moveToNext()) {
                expenses.add(oneTimeExpenseFromCursor(cursor));
            }

            return expenses;
        } finally {
            assert cursor != null;
            cursor.close();
        }
    }

    private static OneTimeExpense oneTimeExpenseFromCursor(Cursor cursor) {
        return new OneTimeExpense(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ONE_TIME_AMOUNT)),
                new Date(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ONE_TIME_DATE)))
        );
    }

    private static ContentValues generateContentValuesOneTimeExpense(OneTimeExpense expense) {
        final ContentValues values = new ContentValues();

        values.put(SQLiteDBHelper.COLUMN_ONE_TIME_DATE, expense.getDate().getTime());
        values.put(SQLiteDBHelper.COLUMN_ONE_TIME_AMOUNT, expense.getAmount());

        return values;
    }

    private static MonthlyExpense monthlyExpenseFromCursor(Cursor cursor) throws JSONException {
        return new MonthlyExpense(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_AMOUNT)),
                new Date(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_STARTDATE))),
                new Date(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_ENDDATE))),
                MonthlyExpense.jsonToModifications(cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_MODIFICATIONS)))
        );
    }

    private static ContentValues generateContentValuesMonthlyExpense(MonthlyExpense expense) throws JSONException {
        final ContentValues values = new ContentValues();

        values.put(SQLiteDBHelper.COLUMN_MONTHLY_STARTDATE, expense.getStartDate().getTime());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_ENDDATE, expense.getEndDate().getTime());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_AMOUNT, expense.getAmount());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_MODIFICATIONS, MonthlyExpense.modificationsToJSON(expense));

        return values;
    }
}
