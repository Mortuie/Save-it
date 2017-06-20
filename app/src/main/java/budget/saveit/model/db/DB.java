package budget.saveit.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import budget.saveit.helper.DateHelper;
import budget.saveit.helper.Logger;
import budget.saveit.model.Expense;
import budget.saveit.model.MonthlyExpense;

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

    private static ContentValues generateContentValuesForExpense(Expense expense) {
        final ContentValues values = new ContentValues();

        values.put(SQLiteDBHelper.COLUMN_EXPENSE_TITLE, expense.getTitle());
        values.put(SQLiteDBHelper.COLUMN_EXPENSE_DATE, expense.getDate().getTime());
        values.put(SQLiteDBHelper.COLUMN_EXPENSE_AMOUNT, expense.getAmount());

        if (expense.isMonthly()) {
            values.put(SQLiteDBHelper.COLUMN_EXPENSE_MONTHLY_ID, expense.getMonthlyID());
        }

        return values;
    }

    private static MonthlyExpense monthlyExpenseFromCursor(Cursor cursor) {
        return new MonthlyExpense(
                cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_TITLE)),
                cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_AMOUNT)),
                new Date(cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_RECURRING_DATE))),
                cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_MONTHLY_MODIFIED)) == 1
        );
    }

    private static ContentValues generateContentValuesForMonthlyExpense(MonthlyExpense expense) {
        final ContentValues values = new ContentValues();

        values.put(SQLiteDBHelper.COLUMN_MONTHLY_TITLE, expense.getTitle());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_RECURRING_DATE, expense.getRecurringDate().getTime());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_AMOUNT, expense.getAmount());
        values.put(SQLiteDBHelper.COLUMN_MONTHLY_MODIFIED, expense.isModified() ? 1 : 0);

        return values;
    }

    public void close() {
        try {
            database.close();
            helper = null;
        } catch (Exception e) {
            Logger.error("Error while closing SQLiteDatabase", e);
        }
    }

    public boolean hasExpensesForDay(Date day) {
        day = DateHelper.sanitiseDate(day);

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT COUNT(*) FROM " + SQLiteDBHelper.TABLE_EXPENSE +
                    " WHERE " + SQLiteDBHelper.COLUMN_EXPENSE_DATE + " = " + day.getTime(), null);

            return cursor.moveToFirst() && cursor.getInt(0) > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getBalanceForDay(Date day) {
        day = DateHelper.sanitiseDate(day);

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT SUM(" + SQLiteDBHelper.COLUMN_EXPENSE_AMOUNT +
                    ") FROM " + SQLiteDBHelper.TABLE_EXPENSE + " WHERE " + SQLiteDBHelper.COLUMN_EXPENSE_DATE +
                    " <= " + day.getTime(), null);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

            return 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<Expense> getExpensesForDay(Date date) {
        date = DateHelper.sanitiseDate(date);

        Cursor cursor = null;
        try {
            List<Expense> expenses = new ArrayList<>();

            cursor = database.query(SQLiteDBHelper.TABLE_EXPENSE, null,
                    SQLiteDBHelper.COLUMN_EXPENSE_DATE + " = " + date.getTime(),
                    null, null, null, null, null);

            while (cursor.moveToNext()) {
                expenses.add(expenseFromCursor(cursor));
            }

            return expenses;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static Expense expenseFromCursor(Cursor cursor) {
        long monthlyID = 0;
        try {
            monthlyID = cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EXPENSE_MONTHLY_ID));
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }

        return new Expense(
                cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EXPENSE_DB_ID)),
                cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EXPENSE_TITLE)),
                cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EXPENSE_AMOUNT)),
                new Date(cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EXPENSE_DATE))),
                monthlyID > 0 ? monthlyID : null
        );
    }

    public void clearDB() {
        database.delete(SQLiteDBHelper.TABLE_EXPENSE, null, null);
        database.delete(SQLiteDBHelper.TABLE_MONTHLY_EXPENSE, null, null);
    }

    public boolean addExpense(Expense expense) {
        if (expense == null) {
            throw new NullPointerException("Expense is null XD");
        }

        long id = database.insert(SQLiteDBHelper.TABLE_EXPENSE, null, generateContentValuesForExpense(expense));
        if (id > 0) {
            expense.setID(id);
            return true;
        }

        return false;
    }

    public long addMonthlyExpense(MonthlyExpense expense) {
        if (expense == null) {
            throw new NullPointerException("Expense is null XD");
        }

        return database.insert(SQLiteDBHelper.TABLE_MONTHLY_EXPENSE, null, generateContentValuesForMonthlyExpense(expense));
    }

    public boolean deleteExpense(Expense expense) {
        return database.delete(SQLiteDBHelper.TABLE_EXPENSE, SQLiteDBHelper.COLUMN_EXPENSE_DB_ID + " = " + expense.getId(), null) > 0;
    }
}
