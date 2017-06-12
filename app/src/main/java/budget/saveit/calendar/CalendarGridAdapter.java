package budget.saveit.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.HashMap;

/**
 * Created by aa on 12/06/17.
 */

public class CalendarGridAdapter extends CaldroidGridAdapter {
    public CalendarGridAdapter(Context context, int month, int year,
                               HashMap<String, Object> caldroidData,
                               HashMap<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
