package budget.saveit.view.expenses;

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

/**
 * Created by aa on 12/06/17.
 */

public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {
    private List<Expense> expenses;
    private Date date;

    public ExpensesRecyclerViewAdapter(DB db, Date date) {
        if (db == null) {
            throw new NullPointerException("DB is null XD");
        }

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.date = date;
        this.expenses = db.getExpensesForDay(date);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_expense_cell, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Expense expense = expenses.get(i);

        viewHolder.expenseTitle.setText(expense.getTitle());
        viewHolder.expenseAmount.setText(-expense.getAmount() + " GBP");
        viewHolder.monthlyIndicator.setVisibility(expense.isMonthly() ? View.VISIBLE : View.GONE);
        viewHolder.positiveIndicator.setImageResource(expense.getAmount() < 0 ? R.mipmap.ic_label_green : R.mipmap.ic_label_red);
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

        public ViewHolder(View v) {
            super(v);

            expenseTitle = (TextView) v.findViewById(R.id.expense_title);
            expenseAmount = (TextView) v.findViewById(R.id.expense_amount);
            monthlyIndicator = (ViewGroup) v.findViewById(R.id.monthly_indicator);
            positiveIndicator = (ImageView) v.findViewById(R.id.positive_indicator);
        }
    }
}
