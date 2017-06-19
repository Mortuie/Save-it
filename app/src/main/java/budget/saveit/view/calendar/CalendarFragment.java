package budget.saveit.view.calendar;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Date;

/**
 * Created by aa on 12/06/17.
 */

public class CalendarFragment extends CaldroidFragment {
    private Date selectedDate;

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CalendarGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }

    @Override
    public void setSelectedDates(Date fromDate, Date toDate) {
        this.selectedDate = fromDate;
        super.setSelectedDates(fromDate, toDate);
    }

    public Date getSelectedDate() {
        return selectedDate;
    }
}