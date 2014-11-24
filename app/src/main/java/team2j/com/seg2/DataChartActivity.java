package team2j.com.seg2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.FillFormatter;
import com.github.mikephil.charting.utils.LimitLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class DataChartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datachart);
        final LineChart chart = (LineChart) findViewById(R.id.chart);

        //triggered when the last json download finished converting
        Core.addOnDataSetsReady(new Core.OnDataSetsReady(){

            @Override
            public void ready() {

                //each DataPoint is converted to a BarEntry
                ArrayList<ArrayList<Entry>> values = new ArrayList<ArrayList<Entry>>();

                for(ArrayList<DataPoint> pairs : Core.DataSets){
                    //data must be sorted by year
                    Collections.sort(pairs , new Comparator<DataPoint>() {
                        @Override
                        public int compare(DataPoint lhs, DataPoint rhs) {
                            return lhs.getYear() > rhs.getYear() ? 1 : 0;
                        }
                    });
                    ArrayList<Entry> entries = new ArrayList<Entry>();
                    for(DataPoint point : pairs){
                        entries.add(new BarEntry(point.getValue(),point.getYear() - 1960));
                    }
                    values.add(entries);
                }

                //this needs to be updated :|
                ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
                for(ArrayList<Entry> entryArrayList : values){
                    LineDataSet dataSet = new LineDataSet(entryArrayList , "GB");
                    dataSet.setColor(Color.RED);
                    dataSet.setHighLightColor(Color.BLACK);
                    dataSet.setLineWidth(5);
                    lineDataSets.add(dataSet);
                }

                //all the X values we can have
                ArrayList<String> years = new ArrayList<String>();
                for(int x = 1960 ; x < 2014;x++){
                    years.add(String.valueOf(x));
                }

                LineData data = new LineData(years ,  lineDataSets);


                //this disables the values label
                chart.setDrawYValues(false);
                //this centers the graph so there isn't blank space at the bottom
                chart.setStartAtZero(false);
                chart.setData(data);
                chart.notifyDataSetChanged();

                
            }
        });



    }
}
