package com.fanhl.linmileagecalendar.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fanhl.linmileagecalendar.MileageDayView;
import com.fanhl.linmileagecalendar.MonthView;
import com.fanhl.linmileagecalendar.R;
import com.fanhl.linmileagecalendar.common.ListAdapter;
import com.fanhl.linmileagecalendar.model.MileageDay;
import com.fanhl.linmileagecalendar.model.MonthData;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 对话框每一月份的adapter
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MonthAdapter extends ListAdapter<MonthAdapter.ViewHolder, MonthData> {
    OnDayClickListener onDayClickListener;

    public MonthAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_month, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(list.get(position));
    }

    public class ViewHolder extends ListAdapter.ViewHolder {
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
            super.bind(data);
            MonthData monthData = (MonthData) data;

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

            monthView.setDate(monthData.getMonth());
            monthView.setData(monthData.getMileageDays());
        }
    }

    public OnDayClickListener getOnDayClickListener() {
        return onDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    public interface OnDayClickListener {
        void onDayClick(Date date);
    }
}
