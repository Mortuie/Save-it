package budget.saveit.view.main.expenses;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import budget.saveit.R;
import budget.saveit.model.Expense;
import budget.saveit.model.db.DB;
import budget.saveit.view.ExpenseEditActivity;
import budget.saveit.view.MainActivity;

/**
 * Created by aa on 12/06/17.
 */

public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {
    private List<Expense> expenses;
    private Date date;
    private Activity activity;
    private DB db;

    public ExpensesRecyclerViewAdapter(Activity activity, DB db, Date date) {
        if (db == null) {
            throw new NullPointerException("DB is null XD");
        }

        if (activity == null) {
            throw new NullPointerException("Activity is null XD");
        }

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.activity = activity;
        this.date = date;
        this.expenses = db.getExpensesForDay(date);
        this.db = db;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_expense_cell, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Expense expense = expenses.get(i);

        viewHolder.expenseTitle.setText(expense.getTitle());
        viewHolder.expenseAmount.setText(-expense.getAmount() + " GBP");
        viewHolder.monthlyIndicator.setVisibility(expense.isMonthly() ? View.VISIBLE : View.GONE);
        viewHolder.positiveIndicator.setImageResource(expense.getAmount() < 0 ? R.mipmap.ic_label_green : R.mipmap.ic_label_red);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!expense.isMonthly()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(R.string.dialog_edit_expense_title);
                    builder.setItems(R.array.dialog_edit_expense_choices, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0: {
                                    Intent startIntent = new Intent(viewHolder.view.getContext(), ExpenseEditActivity.class);
                                    startIntent.putExtra("date", expense.getDate());
                                    startIntent.putExtra("expense", expense);

                                    ActivityCompat.startActivityForResult(activity, startIntent, MainActivity.ADD_EXPENSE_ACTIVITY_CODE, null);
                                }
                                case 1: {
                                    if (db.deleteExpense(expense)) {
                                        Intent intent = new Intent(MainActivity.INTENT_EXPENSE_DELETED);
                                        intent.putExtra("expense", expense);
                                        LocalBroadcastManager.getInstance(activity.getApplicationContext()).sendBroadcast(intent);
                                    }
                                }
                            }
                        }
                    });

                    builder.show();
                }
            }
        };

        viewHolder.view.setOnClickListener(onClickListener);

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickListener.onClick(v);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public Date getDate() {
        return date;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView expenseTitle;
        public final TextView expenseAmount;
        public final ViewGroup monthlyIndicator;
        public final ImageView positiveIndicator;
        public final View view;

        public ViewHolder(View v) {
            super(v);

            view = v;
            expenseTitle = (TextView) v.findViewById(R.id.expense_title);
            expenseAmount = (TextView) v.findViewById(R.id.expense_amount);
            monthlyIndicator = (ViewGroup) v.findViewById(R.id.monthly_indicator);
            positiveIndicator = (ImageView) v.findViewById(R.id.positive_indicator);
        }
    }
}
