package team2j.com.seg2;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.utils.FillFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


public class DataChartFragment extends Fragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.chart_fragment, container, false);

        this.view = view;
        super.onCreate(savedInstanceState);


       //Core. chart = (BarChart) view.findViewById(R.id.chart);

//       Core. chart.setNoDataText("Drawing data , please wait.");



        return view;

    }

    public void renderData(ArrayList<DataPoint> points , ArrayList<DataPoint> comparingTo){


        final BarChart chart = (BarChart) view.findViewById(R.id.chart);

        //each DataPoint is converted to a BarEntry
        ArrayList<ArrayList<BarEntry>> values = new ArrayList<ArrayList<BarEntry>>();

        //data must be sorted by year
        Collections.sort(points, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint lhs, DataPoint rhs) {
                return lhs.getYear() > rhs.getYear() ? 1 : 0;
            }
        });


        int from = Core.selectedFrom == null ? 1960 : Core.selectedFrom;
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for(DataPoint point : points){
            entries.add(new BarEntry(point.getValue(),point.getYear() - from));

        }
        values.add(entries);

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();

        if(comparingTo!=null){
            Collections.sort(comparingTo, new Comparator<DataPoint>() {
                @Override
                public int compare(DataPoint lhs, DataPoint rhs) {
                    return lhs.getYear() > rhs.getYear() ? 1 : 0;
                }
            });


            for(DataPoint point : comparingTo){
                entries1.add(new BarEntry(point.getValue(),point.getYear() - from));

            }
            values.add(entries1);
        }


        Random rnd = new Random();
        boolean first = true;
        int n = 0;
        //this needs to be updated :|
        ArrayList<BarDataSet> BarDataSets = new ArrayList<BarDataSet>();
        for(ArrayList<BarEntry> entryArrayList : values){
            BarDataSet dataSet = new BarDataSet(entryArrayList  , ""+rnd.nextInt());
            n++;
            dataSet.setColor(first ? getResources().getColor(R.color.brandColor) : Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)) );


            //dataSet.setCircleColor(dataSet.getColor());
            first = false;
            //dataSet.setLineWidth(2.5f);

            //dataSet.setCircleSize(1f);
            //dataSet.setDrawCubic(true);
            // dataSet.setCubicIntensity(10);

            BarDataSets.add(dataSet);
        }

        //all the X values we can have
        ArrayList<String> years = new ArrayList<String>();


        int start =Core.selectedFrom == null ? 1960 : Core.selectedFrom;
        int end = Core.selectedTo == null ? 2014 : Core.selectedTo;
        for(int x = start ; x <= end+1;x++){

            years.add(String.valueOf(x));
        }

        BarData data = new BarData(years ,  BarDataSets);



        //this disables the values label

       chart.set3DEnabled(true);
        chart.setDrawYValues(false);
        //this centers the graph so there isn't blank space at the bottom
        chart.setStartAtZero(false);
        chart.setData(data);
        chart.invalidate();


        //this handler was called from the network thread so we must go back to the ui
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //chart.invalidate();

                chart.animateY(1400);
            }
        });

    }
}
