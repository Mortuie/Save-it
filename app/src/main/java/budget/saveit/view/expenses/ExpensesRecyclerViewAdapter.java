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
import budget.saveit.model.OneTimeExpense;
import budget.saveit.model.db.DB;

/**
 * Created by aa on 12/06/17.
 */

public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {
    private List<Expense> expenses = new ArrayList<>();

    public ExpensesRecyclerViewAdapter(DB db, Date date) {
        if (db == null) {
            throw new NullPointerException("DB is null XD");
        }

        if (date == null) {
            throw new NullPointerException("Date is null XD");
        }

        List<OneTimeExpense> expenses = db.getOneTimeExpensesForDay(date);
        this.expenses.addAll(expenses);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_expense_cell, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText("Amount: " + expenses.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.expense_text_view);
        }
    }
}
