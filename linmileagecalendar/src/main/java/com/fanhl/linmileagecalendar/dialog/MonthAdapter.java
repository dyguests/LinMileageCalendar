package com.fanhl.linmileagecalendar.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanhl.linmileagecalendar.MileageDayView;
import com.fanhl.linmileagecalendar.MonthView;
import com.fanhl.linmileagecalendar.R;
import com.fanhl.linmileagecalendar.common.ListAdapter;
import com.fanhl.linmileagecalendar.model.MileageDay;
import com.fanhl.linmileagecalendar.model.MonthData;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 对话框每一月份的adapter
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MonthAdapter extends ListAdapter<MonthAdapter.ViewHolder, MonthData> {
    public static final String TAG = MonthAdapter.class.getSimpleName();

    private final Date selectedDate;

    OnDayClickListener onDayClickListener;

    public MonthAdapter(Context context, RecyclerView recyclerView, Date selectedDate) {
        super(context, recyclerView);
        if (selectedDate == null) {
            selectedDate = new Date();
        }
        this.selectedDate = selectedDate;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_month, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(list.get(position));
    }

    @Override public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        ((MonthData) holder.getData()).deleteObservers();
    }

    public OnDayClickListener getOnDayClickListener() {
        return onDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    public class ViewHolder extends ListAdapter.ViewHolder implements Observer {
        private final TextView monthTotalTv;
        private final MonthView monthView;

        public ViewHolder(View itemView) {
            super(itemView);
            monthTotalTv = ((TextView) itemView.findViewById(R.id.monthTotalTv));
            monthView = ((MonthView) itemView.findViewById(R.id.monthView));

            monthView.setOnDayClickListener(new MonthView.OnDayClickListener() {
                @Override public void onDayClick(MileageDayView dayView) {
                    if (onDayClickListener != null) {
                        onDayClickListener.onDayClick(dayView.getDate());
                    }
                }
            });
        }

        @Override public void bind(Object data) {
            if (this.getData() != null) {
                ((MonthData) this.getData()).deleteObservers();
            }

            super.bind(data);

            MonthData monthData = (MonthData) data;
            monthData.addObserver(this);

            setMonthTotal(monthData);
            if (DateUtil.isSameMonth(monthData.getMonth(),selectedDate)) {
                monthView.setSelectedDate(selectedDate);
            }
            monthView.setDate(monthData.getMonth());
            monthView.setData(monthData.getMileageDays());
        }

        @Override public void update(Observable o, Object arg) {
            Log.d(TAG, "update monthView.setData(((MonthData) o).getMileageDays());");
            List<MileageDay> mileageDays = ((MonthData) o).getMileageDays();

            setMonthTotal((MonthData) getData());
            monthView.setData(mileageDays);
        }

        private void setMonthTotal(MonthData monthData) {
            String month = DateUtil.date2str(monthData.getMonth(), DateUtil.FORMAT_M);

            String mileage;
            if (monthData.getMileageDays() != null) {
                float mileageTotal = 0;
                for (MileageDay mileageDay : monthData.getMileageDays()) {
                    if (mileageDay.getMileage() != null) {
                        mileageTotal += mileageDay.getMileage();
                    }
                }
                mileage = new DecimalFormat("#.#").format(mileageTotal);
            } else {
                mileage = "--";
            }

            monthTotalTv.setText(context.getResources().getString(R.string.item_month_month_total, month, mileage));
        }
    }

    public interface OnDayClickListener {
        void onDayClick(Date date);
    }
}
