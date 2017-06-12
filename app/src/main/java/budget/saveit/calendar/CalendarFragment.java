package budget.saveit.calendar;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * Created by aa on 12/06/17.
 */

public class CalendarFragment extends CaldroidFragment {
    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CalendarGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }
}