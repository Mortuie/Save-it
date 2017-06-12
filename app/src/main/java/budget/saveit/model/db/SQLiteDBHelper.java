package budget.saveit.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aa on 12/06/17.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {

    protected static final String TABLE_ONE_TIME_EXPENSE = "onetimeexpense";
    protected static final String COLUMN_ONE_TIME_DB_ID = "_expense_id";
    protected static final String COLUMN_ONE_TIME_AMOUNT = "amount";
    protected static final String COLUMN_ONE_TIME_DATE = "date";

    protected static final String TABLE_MONTHLY_EXPENSE = "monthly expense";
    protected static final String COLUMN_MONTHLY_DB_ID          = "_expense_id";
    protected static final String COLUMN_MONTHLY_AMOUNT         = "amount";
    protected static final String COLUMN_MONTHLY_STARTDATE      = "startDate";
    protected static final String COLUMN_MONTHLY_ENDDATE        = "endDate";
    protected static final String COLUMN_MONTHLY_MODIFICATIONS  = "modifications";

    private static final String DATABASE_NAME = "saveit.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ONE_TIME_EXPENSE + "(" + COLUMN_ONE_TIME_DB_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ONE_TIME_AMOUNT +
                " INTEGER NOT NULL, " + COLUMN_ONE_TIME_DATE + " INTEGER NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_MONTHLY_EXPENSE + "(" +
                COLUMN_MONTHLY_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MONTHLY_AMOUNT + " INTEGER NOT NULL, " +
                COLUMN_MONTHLY_ENDDATE + " INTEGER NOT NULL, " +
                COLUMN_MONTHLY_MODIFICATIONS + " TEXT NOT NULL, " +
                COLUMN_MONTHLY_STARTDATE + " INTEGER NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
