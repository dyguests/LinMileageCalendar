package com.fanhl.linmileagecalendar.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 对话框每一月份的adapter
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private final Context context;

    public MonthAdapter(Context context) {
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
