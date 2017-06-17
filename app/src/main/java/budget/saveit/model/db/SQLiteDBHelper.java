package budget.saveit.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aa on 12/06/17.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {

    protected static final String TABLE_EXPENSE             = "expense";
    protected static final String COLUMN_EXPENSE_DB_ID      = "_expense_id";
    protected static final String COLUMN_EXPENSE_TITLE      = "title";
    protected static final String COLUMN_EXPENSE_AMOUNT     = "amount";
    protected static final String COLUMN_EXPENSE_DATE       = "date";
    protected static final String COLUMN_EXPENSE_MONTHLY_ID = "monthly_id";

    protected static final String TABLE_MONTHLY_EXPENSE         = "monthlyexpense";
    protected static final String COLUMN_MONTHLY_DB_ID          = "_expense_id";
    protected static final String COLUMN_MONTHLY_TITLE          = "title";
    protected static final String COLUMN_MONTHLY_AMOUNT         = "amount";
    protected static final String COLUMN_MONTHLY_RECURRING_DATE = "recurringDate";
    protected static final String COLUMN_MONTHLY_MODIFIED       = "modified";

    private static final String DATABASE_NAME = "saveit.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_EXPENSE + "("
                + COLUMN_EXPENSE_DB_ID + " integer primary key autoincrement, "
                + COLUMN_EXPENSE_TITLE + " text not null, "
                + COLUMN_EXPENSE_AMOUNT + " integer not null, "
                + COLUMN_EXPENSE_DATE + " integer not null, "
                + COLUMN_EXPENSE_MONTHLY_ID + " integer null );");

        db.execSQL("CREATE INDEX D_i on "+ TABLE_EXPENSE +"("+ COLUMN_EXPENSE_DATE +");");

        db.execSQL("create table "
                + TABLE_MONTHLY_EXPENSE + "("
                + COLUMN_MONTHLY_DB_ID + " integer primary key autoincrement, "
                + COLUMN_MONTHLY_TITLE + " text not null, "
                + COLUMN_MONTHLY_AMOUNT + " integer not null, "
                + COLUMN_MONTHLY_MODIFIED + " integer not null, "
                + COLUMN_MONTHLY_RECURRING_DATE + " integer not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
