package team2j.com.seg2;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ComparasionChartFragment extends Fragment {
    View view;
    public LineChart chart;

    public String countryName1 = "";
    public String countryName2 = "";

    Random rnd = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.compare_chart_fragment, container, false);

        this.view = view;
        super.onCreate(savedInstanceState);


        chart = (LineChart) view.findViewById(R.id.chart);
        //Core. chart = (LineChart) view.findViewById(R.id.chart);

//       Core. chart.setNoDataText("Drawing data , please wait.");
        Button boundsButton = (Button)view.findViewById(R.id.boundsButton);
        boundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.setStartAtZero(!chart.isStartAtZeroEnabled());
                chart.invalidate();
            }
        });

        Button saveButton = (Button)view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.saveToGallery("chart" + String.valueOf(rnd.nextInt()) , 100);
            }
        });


        return view;

    }

    public void renderData(ArrayList<DataPoint> points , ArrayList<DataPoint> comparingTo){



        //each DataPoint is converted to a LineEntry
        ArrayList<ArrayList<Entry>> values = new ArrayList<ArrayList<Entry>>();

        //data must be sorted by year
        Collections.sort(points, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint lhs, DataPoint rhs) {
                return lhs.getYear() > rhs.getYear() ? 1 : 0;
            }
        });


        int from = Core.selectedFrom == null ? 1960 : Core.selectedFrom;
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(DataPoint point : points){
            entries.add(new Entry(point.getValue(),point.getYear() - from));
        }
        values.add(entries);

        ArrayList<Entry> entries1 = new ArrayList<Entry>();

            Collections.sort(comparingTo, new Comparator<DataPoint>() {
                @Override
                public int compare(DataPoint lhs, DataPoint rhs) {
                    return lhs.getYear() > rhs.getYear() ? 1 : 0;
                }
            });


            for(DataPoint point : comparingTo){
                entries1.add(new Entry(point.getValue(),point.getYear() - from));
            }
            values.add(entries1);


        




        boolean first = true;
        int n = 0;
        //this needs to be updated :|
        ArrayList<LineDataSet> LineDataSets = new ArrayList<LineDataSet>();
        for(ArrayList<Entry> entryArrayList : values){
            LineDataSet dataSet = new LineDataSet(entryArrayList  , first ? countryName1 : countryName2);
            n++;
            dataSet.setColor(first ? getResources().getColor(R.color.brandColor) : Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)) );


            dataSet.setCircleColor(dataSet.getColor());
            first = false;
            dataSet.setLineWidth(2.5f);

            dataSet.setCircleSize(1f);
            //dataSet.setDrawCubic(true);
            // dataSet.setCubicIntensity(10);

            LineDataSets.add(dataSet);
        }

        //all the X values we can have
        ArrayList<String> years = new ArrayList<String>();


        int start =Core.selectedFrom == null ? 1960 : Core.selectedFrom;
        int end = Core.selectedTo == null ? 2014 : Core.selectedTo;
        for(int x = start ; x <= end+1;x++){

            years.add(String.valueOf(x));
        }

        LineData data = new LineData(years ,  LineDataSets);



        //this disables the values label

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
