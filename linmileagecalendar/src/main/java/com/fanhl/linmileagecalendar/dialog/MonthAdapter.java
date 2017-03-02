package com.fanhl.linmileagecalendar.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanhl.linmileagecalendar.MonthView;
import com.fanhl.linmileagecalendar.R;
import com.fanhl.linmileagecalendar.common.ListAdapter;
import com.fanhl.linmileagecalendar.model.MonthData;

/**
 * 对话框每一月份的adapter
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MonthAdapter extends ListAdapter<MonthAdapter.ViewHolder, MonthData> {

    public MonthAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_month, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }

    public class ViewHolder extends ListAdapter.ViewHolder {

        private final TextView monthTotalTv;
        private final MonthView monthView;

        public ViewHolder(View itemView) {
            super(itemView);
            monthTotalTv = ((TextView) itemView.findViewById(R.id.monthTotalTv));
            monthView = ((MonthView) itemView.findViewById(R.id.monthView));

//            monthView.setMonthHeaderShowed(false);
        }
    }
}
