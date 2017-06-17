package budget.saveit.model;

/**
 * Created by aa on 17/06/17.
 */

public abstract class Expense {
    protected String title;

    public Expense(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is empty XD");
        }

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
