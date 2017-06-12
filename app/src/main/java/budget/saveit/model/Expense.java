package budget.saveit.model;

/**
 * Created by aa on 12/06/17.
 */

public class Expense {
    private final int amount;

    public Expense(final int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Expense should be greater than 0!");
        }

        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isRevenue() {
        return amount < 0;
    }
}
