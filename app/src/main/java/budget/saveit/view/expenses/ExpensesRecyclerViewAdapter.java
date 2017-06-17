package budget.saveit.view.expenses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import budget.saveit.R;
import budget.saveit.model.Expense;
import budget.saveit.model.MonthlyExpense;
import budget.saveit.model.OneTimeExpense;
import budget.saveit.model.db.DB;

/**
 * Created by aa on 12/06/17.
 */

public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {
    private List<Expense> expenses = new ArrayList<>();
    private Date date;

    public ExpensesRecyclerViewAdapter(DB db, Date date) {
        if (db == null) {
            throw new NullPointerException("DB is null XD");
        }

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        this.date = date;
        this.expenses.addAll(db.getOneTimeExpensesForDay(date));
        this.expenses.addAll(db.getMonthlyExpensesForDay(date));
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

        if (expense instanceof OneTimeExpense) {
            drawOneTimeExpense((OneTimeExpense) expense, viewHolder);
        } else if (expense instanceof MonthlyExpense) {
            drawMonthlyExpense((MonthlyExpense) expense, viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView expenseTitle;
        public final TextView expenseAmount;

        public ViewHolder(View v) {
            super(v);

            expenseTitle = (TextView) v.findViewById(R.id.expense_title);
            expenseAmount = (TextView) v.findViewById(R.id.expense_amount);
        }
    }

    private void drawOneTimeExpense(OneTimeExpense expense, ViewHolder viewHolder) {
        viewHolder.expenseAmount.setText("£" + expense.getAmount());
    }

    private void drawMonthlyExpense(MonthlyExpense expense, ViewHolder viewHolder) {
        viewHolder.expenseAmount.setText("£" + expense.getAmountForMonth(date));
    }
}
