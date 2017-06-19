package budget.saveit.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hampe on 19 June 2017.
 */

public class DatePickerDialogFragment extends DialogFragment {
    private Date originalDate;
    private DatePickerDialog.OnDateSetListener listener;

    public DatePickerDialogFragment() {
        throw new RuntimeException("DatePickerDialogFragment needs to be instantiated with date and listener constructor XD");
    }

    @SuppressLint("ValidFragment")
    public DatePickerDialogFragment(Date originalDate, DatePickerDialog.OnDateSetListener listener) {
        if (originalDate == null) {
            throw new NullPointerException("originalDate is null XD");
        }

        if (listener == null) {
            throw new NullPointerException("listener is null XD");
        }

        this.originalDate = originalDate;
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.setTime(originalDate);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
}
