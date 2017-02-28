package com.fanhl.linmileagecalendar.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.fanhl.linmileagecalendar.MonthView;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MonthView monthView;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initData();
        refreshData();
    }

    private void assignViews() {
        this.monthView = (MonthView) findViewById(R.id.monthView);
    }

    private void initData() {
        monthView.setDate(DateUtil.getFirstDayInMonth(new Date()));
        random = new Random();
    }

    private void refreshData() {
        new AsyncTask<Void, Void, Void>() {

            private List<Report> list;

            @Override protected Void doInBackground(Void... params) {
                list = getReports();

                return null;
            }

            @Override protected void onPostExecute(Void aVoid) {
                bindData(list);
            }
        }.execute();
    }

    private void bindData(List<Report> list) {

    }

    @NonNull private ArrayList<Report> getReports() {
        ArrayList<Report> reports = new ArrayList<>();

        Date date = new Date();
        Date startDate = DateUtil.getFirstDayInMonth(date);
        int numberOfDayInMonth = DateUtil.getNumberOfDayInMonth(startDate);
        for (int i = 0; i < numberOfDayInMonth; i++) {
            if (random.nextInt(100) > 20) {
                Report report = new Report();
                report.setDate(startDate);
                report.setMileage(random.nextDouble() * 100);

                reports.add(report);
            }

            DateUtil.addDay(startDate, 1);
        }

        return reports;
    }
}
