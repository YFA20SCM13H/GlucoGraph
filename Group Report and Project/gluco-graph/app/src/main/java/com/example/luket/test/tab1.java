package com.example.luket.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;

/**
 * Created by Luket on 27/12/2017.
 */

public class tab1 extends Fragment {

    DatabaseHelper myDb;
    LineGraphSeries<DataPoint> series;

    SQLiteDatabase sqLiteDatabase;
    GraphView graph;

    TextView GlucoseLvl;



//    todo clean up code & add tap listeners to graph

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);



    }

    public void onStart() {
        super.onStart();
        initControls();
        initGraph();

    }



    private void initControls() {

        myDb = new DatabaseHelper(getActivity());
        sqLiteDatabase = myDb.getReadableDatabase();

    }
    public void initGraph() {
        graph = getView().findViewById(R.id.graph);
        GlucoseLvl = getView().findViewById(R.id.GlucoseLvl);

        series = new LineGraphSeries<DataPoint>(getData());
        graph.addSeries(series);


        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                double glucose = dataPoint.getY();
                String msg = Double.valueOf(glucose).toString();
                GlucoseLvl.setText("Blood Glucose Level:\t"+msg);


            }
        });
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public DataPoint[] getData() {
        Cursor res = myDb.getAllData();

        DataPoint[] dp = new DataPoint[res.getCount()];

        if (res.getCount() == 0) {
            showMessage("Error", "Nothing Found");
        }
        for (int i = 0; i < res.getCount(); i++) {
            res.moveToNext();
            dp[i] = new DataPoint(res.getInt(0), res.getFloat(1));
        }

        return dp;
    }



}




