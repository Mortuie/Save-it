package budget.saveit.view.monthly;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import budget.saveit.R;
import budget.saveit.model.MonthlyExpense;
import budget.saveit.model.db.DB;

/**
 * Created by aa on 22/06/17.
 */

public class MonthlyRecyclerViewAdapter extends RecyclerView.Adapter<MonthlyRecyclerViewAdapter.ViewHolder> {
    private Activity activity;
    private List<MonthlyExpense> expenses;

    public MonthlyRecyclerViewAdapter(Activity activity, DB db) {
        if (db == null || activity == null) {
            throw new NullPointerException("db or activity is null XD");
        }

        this.activity = activity;
        this.expenses = db.getAllMonthlyExpenses();
    }

    @Override
    public MonthlyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_monthly_expense_cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MonthlyRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final MonthlyExpense expense = expenses.get(position);

        viewHolder.expenseTitle.setText(expense.getTitle());
        viewHolder.expenseAmount.setText(-expense.getAmount() + " GBP");
        viewHolder.positiveIndicator.setImageResource(expense.getAmount() < 0 ? R.mipmap.ic_label_green : R.mipmap.ic_label_red);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView expenseTitle;
        public final TextView expenseAmount;
        public final ImageView positiveIndicator;
        public final View view;

        public ViewHolder(View v) {
            super(v);

            view = v;
            expenseTitle = (TextView) v.findViewById(R.id.expense_title);
            expenseAmount = (TextView) v.findViewById(R.id.expense_amount);
            positiveIndicator = (ImageView) v.findViewById(R.id.positive_indicator);
        }
    }
}
