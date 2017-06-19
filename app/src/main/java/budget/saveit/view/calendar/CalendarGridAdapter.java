package budget.saveit.view.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import budget.saveit.R;
import budget.saveit.helper.ParameterKeys;
import budget.saveit.helper.Parameters;
import budget.saveit.model.db.DB;
import hirondelle.date4j.DateTime;

/**
 * Created by aa on 12/06/17.
 */

public class CalendarGridAdapter extends CaldroidGridAdapter {
    private DB db;
    private int baseBalance;

    public CalendarGridAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);

        db = new DB(context.getApplicationContext());
        baseBalance = Parameters.getInstance(context).getInt(ParameterKeys.BASE_BALANCE, 0);
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();

        super.finalize();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView = convertView;
        ViewData viewData = null;

        // For reuse
        if (convertView == null) {
            cellView = createView(parent);
            viewData = new ViewData();

            viewData.day = (TextView) cellView.findViewById(R.id.grid_cell_tv1);
            viewData.amount = (TextView) cellView.findViewById(R.id.grid_cell_tv2);
            viewData.cellColorIndicator = cellView.findViewById(R.id.cell_color_indicator);
        } else {
            viewData = (ViewData) cellView.getTag();
        }

        // Get dateTime of this cell
        DateTime dateTime = this.datetimeList.get(position);
        boolean isToday = dateTime.equals(getToday());
        boolean isDisabled = (minDateTime != null && dateTime.lt(minDateTime)) ||
                (maxDateTime != null && dateTime.gt(maxDateTime)) ||
                (disableDates != null && disableDatesMap.containsKey(dateTime));
        boolean isOutOfMonth = dateTime.getMonth() != month;

        TextView tv1 = viewData.day;
        TextView tv2 = viewData.amount;
        View cellColorIndicator = viewData.cellColorIndicator;

        // Customize for disabled dates and date outside min/max dates
        if (isDisabled) {
            if (!viewData.isDisabled) {
                tv1.setTextColor(context.getResources().getColor(R.color.calendar_cell_disabled_text_color));
                tv2.setTextColor(context.getResources().getColor(R.color.calendar_cell_disabled_text_color));
                cellView.setBackgroundResource(android.R.color.white);

                viewData.isDisabled = true;
                viewData.isToday = false;
                viewData.isSelected = false;
            }
        } else if (viewData.isDisabled) {
            tv1.setTextColor(context.getResources().getColor(R.color.primary_text));
            tv2.setTextColor(context.getResources().getColor(R.color.secondary_text));
            cellView.setBackgroundResource(R.drawable.custom_grid_cell_drawable);

            viewData.isDisabled = false;
            viewData.isSelected = false;
            viewData.isToday = false;
        }

        if (isOutOfMonth && !isDisabled) {
            if (!viewData.isOutOfMonth) {
                tv1.setTextColor(context.getResources().getColor(R.color.divider));
                tv2.setTextColor(context.getResources().getColor(R.color.divider));

                viewData.isOutOfMonth = true;
            }
        } else if (!isDisabled && viewData.isOutOfMonth) {
            tv1.setTextColor(context.getResources().getColor(R.color.primary_text));
            tv2.setTextColor(context.getResources().getColor(R.color.secondary_text));

            viewData.isOutOfMonth = false;
        }

        // Today's cell
        if (isToday) {
            // Customize for selected dates
            if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
                if (!viewData.isToday || !viewData.isSelected) {
                    cellView.setBackgroundResource(R.drawable.custom_grid_today_cell_selected_drawable);

                    viewData.isToday = true;
                    viewData.isSelected = true;
                }
            } else if (!viewData.isToday || viewData.isSelected) {
                cellView.setBackgroundResource(R.drawable.custom_grid_today_cell_drawable);

                viewData.isToday = true;
                viewData.isSelected = false;
            }
        } else {
            // Customize for selected dates
            if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
                if (viewData.isToday || !viewData.isSelected) {
                    cellView.setBackgroundResource(R.drawable.custom_grid_cell_selected_drawable);

                    viewData.isToday = false;
                    viewData.isSelected = true;
                }
            } else if (viewData.isToday || viewData.isSelected) {
                cellView.setBackgroundResource(R.drawable.custom_grid_cell_drawable);

                viewData.isToday = false;
                viewData.isSelected = false;
            }
        }

        tv1.setText("" + dateTime.getDay());

        Date date = new Date(dateTime.getMilliseconds(TimeZone.getTimeZone("UTC")));
        if (db.hasExpensesForDay(date)) {
            int balance = db.getBalanceForDay(date);

            if (!viewData.containsExpenses) {
                tv2.setVisibility(View.VISIBLE);
                cellColorIndicator.setVisibility(View.VISIBLE);

                viewData.containsExpenses = true;
            }

            tv2.setText((baseBalance - balance) + "");

            if (balance > 0) {
                cellColorIndicator.setBackgroundResource(R.color.budget_red);
            } else if (balance <= 0) {
                cellColorIndicator.setBackgroundResource(R.color.budget_green);
            }

            // Apply margin to the color indicator if it's today's cell since there's a border
            if (isToday && !viewData.colorIndicatorMarginForToday) {
                int marginDimen = context.getResources().getDimensionPixelOffset(R.dimen.grid_cell_today_border_size);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellColorIndicator.getLayoutParams());
                params.setMargins(0, marginDimen, marginDimen, 0);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                cellColorIndicator.setLayoutParams(params);

                viewData.colorIndicatorMarginForToday = true;
            } else if (!isToday && viewData.colorIndicatorMarginForToday) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellColorIndicator.getLayoutParams());
                params.setMargins(0, 0, 0, 0);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                cellColorIndicator.setLayoutParams(params);

                viewData.colorIndicatorMarginForToday = false;
            }
        } else if (viewData.containsExpenses) {
            cellColorIndicator.setVisibility(View.GONE);
            tv2.setVisibility(View.INVISIBLE);

            viewData.containsExpenses = false;
        }

        cellView.setTag(viewData);
        return cellView;
    }

    private View createView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_grid_cell, parent, false);
    }

    public static class ViewData {
        public TextView day;
        public TextView amount;
        public View cellColorIndicator;
        public boolean isDisabled = false;
        public boolean isOutOfMonth = false;
        public boolean isToday = false;
        public boolean isSelected = false;
        public boolean containsExpenses = false;
        public boolean colorIndicatorMarginForToday = false;
    }
}