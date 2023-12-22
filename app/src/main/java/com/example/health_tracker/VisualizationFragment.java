package com.example.health_tracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class VisualizationFragment extends Fragment {


    ArrayList<Entry> entries = new ArrayList<>();
    List<DataEntry> seriesDataQuality = new ArrayList<>();
    List<DataEntry> seriesDataHoursSlept = new ArrayList<>();
    List<DataEntry> seriesDataHoursExercised = new ArrayList<>();
    List<DataEntry> seriesDataWeight = new ArrayList<>();

    AppDatabase db;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visualization, container, false);

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "entry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        entries.addAll(db.entryDao().getAll());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.set(e1.year, e1.month, e1.day, e1.hourOfDay, e1.minute);
                cal2.set(e2.year, e2.month, e2.day, e2.hourOfDay, e2.minute);
                return cal1.compareTo(cal2);
            }
        });

        for(Entry entry : entries){
            Calendar calendar = Calendar.getInstance();
            calendar.set(entry.year, entry.month, entry.day, entry.hourOfDay, entry.minute);
            Date date = calendar.getTime();
            String formattedDateTime = sdf.format(date);


            seriesDataQuality.add(new ValueDataEntry(formattedDateTime, entry.quality));
            seriesDataHoursExercised.add(new ValueDataEntry(formattedDateTime, entry.hoursExercised));
            seriesDataHoursSlept.add(new ValueDataEntry(formattedDateTime, entry.hoursSlept));
            seriesDataWeight.add(new ValueDataEntry(formattedDateTime, entry.weight));
        }

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        //START CHARTING//
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Health Data Visualized");

        cartesian.yAxis(0).title("Hours");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        Set set = Set.instantiate();
        set.data(seriesDataHoursSlept);

        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Sleep Hours"); // change this
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
        //END CHARTING

        Button buttonHoursSlept = view.findViewById(R.id.buttonHoursSlept);
        buttonHoursSlept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.data(seriesDataHoursSlept);

                cartesian.yAxis(0).title("Hours");
                series1.name("Hours Slept");
            }
        });
        Button buttonHoursExercised = view.findViewById(R.id.buttonHoursExercised);
        buttonHoursExercised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set.data(seriesDataHoursExercised);

                cartesian.yAxis(0).title("Hours");
                series1.name("Hours Exercised");

            }
        });
        Button buttonQuality = view.findViewById(R.id.buttonQuality);
        buttonQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set.data(seriesDataQuality);
                cartesian.yAxis(0).title("Scale 1-5");
                series1.name("Quality");
            }
        });
        Button buttonWeight = view.findViewById(R.id.buttonWeight);
        buttonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.data(seriesDataWeight);

                cartesian.yAxis(0).title("Weight");
                series1.name("Lbs");
            }
        });

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.popBackStack();
            }
        });
        return view;
    }

    VisualizationListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (VisualizationListener) context;
    }

    interface VisualizationListener {
        void popBackStack();
    }
}