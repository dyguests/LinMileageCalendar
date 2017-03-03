package com.fanhl.linmileagecalendar.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fanhl.linmileagecalendar.MileageDayView;
import com.fanhl.linmileagecalendar.MonthView;
import com.fanhl.linmileagecalendar.dialog.MileageCalendarDialogFragment;
import com.fanhl.linmileagecalendar.dialog.MonthAdapter;
import com.fanhl.linmileagecalendar.model.MileageDay;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MonthView monthView;
    private Random random;
    private Date selectedDate;

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

        selectedDate = new Date();
        findViewById(R.id.mileageBtn)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        showCalendarDialog();
                    }
                });
    }

    private void showCalendarDialog() {
        MileageCalendarDialogFragment.newInstance(selectedDate, new MonthAdapter.OnDayClickListener() {
            @Override public void onDayClick(Date date) {
                selectedDate = date;
                Toast.makeText(MainActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        }).show(getSupportFragmentManager(), MileageCalendarDialogFragment.TAG);
    }

    private void initData() {
        monthView.setDate(DateUtil.getFirstDayInMonth(new Date()));
        random = new Random();

        monthView.setOnDayClickListener(new MonthView.OnDayClickListener() {
            @Override public void onDayClick(MileageDayView dayView) {
                Toast.makeText(MainActivity.this, dayView.getDate().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshData() {
        new AsyncTask<Void, Void, Void>() {

            private List<Report> list;

            @Override protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list = getReports();

                return null;
            }

            @Override protected void onPostExecute(Void aVoid) {
                bindData(list);
            }
        }.execute();
    }

    private void bindData(List<Report> list) {
        List<MileageDay> data = new ArrayList<>();

//        Date date = monthView.getDate();
//        Date startDate = DateUtil.getFirstDayInMonth(date);
//        int numberOfDayInMonth = DateUtil.getNumberOfDayInMonth(startDate);
//        for (int i = 0; i < numberOfDayInMonth; i++) {
//            Report report = CollectionUtil.binarySearch(list, startDate, new CollectionUtil.BinarySearchComparator<Report, Date>() {
//                @Override public int compare(Report report, Date date) {
//                    long keyTime = date.getTime();
//                    long reportTime = report.getDate().getTime();
//                    long l = keyTime / 60 / 60 / 1000 - reportTime / 60 / 60 / 1000;
//                    if (l < 0) {
//                        return -1;
//                    } else if (l > 0) {
//                        return 1;
//                    } else {
//                        return 0;
//                    }
//                }
//            });
//
//            DateUtil.addDay(startDate, 1);
//        }

        for (Report report : list) {
            data.add(new MileageDay(report.getDate(), (float) report.getMileage()));
        }

        monthView.setData(data);
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

            startDate = DateUtil.addDay(startDate, 1);
        }

        return reports;
    }
}
