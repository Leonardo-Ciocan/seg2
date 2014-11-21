package team2j.com.seg2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
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
        final BarChart chart = (BarChart) findViewById(R.id.chart);

        //triggered when the last json download finished converting
        Core.addOnDataSetsReady(new Core.OnDataSetsReady(){

            @Override
            public void ready() {

                //each DataPoint is converted to a BarEntry
                ArrayList<ArrayList<BarEntry>> values = new ArrayList<ArrayList<BarEntry>>();

                for(ArrayList<DataPoint> pairs : Core.DataSets){
                    //data must be sorted by year
                    Collections.sort(pairs , new Comparator<DataPoint>() {
                        @Override
                        public int compare(DataPoint lhs, DataPoint rhs) {
                            return lhs.getYear() > rhs.getYear() ? 1 : 0;
                        }
                    });
                    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                    for(DataPoint point : pairs){
                        entries.add(new BarEntry(point.getValue(),point.getYear() - 1960));
                    }
                    values.add(entries);
                }

                //this needs to be updated :|
                ArrayList<BarDataSet> lineDataSets = new ArrayList<BarDataSet>();
                for(ArrayList<BarEntry> entryArrayList : values){
                    BarDataSet dataSet = new BarDataSet(entryArrayList , "GB");
                    dataSet.setColor(Color.RED);
                   // dataSet.setLineWidth(5);
                    lineDataSets.add(dataSet);
                }

                //all the X values we can have
                ArrayList<String> years = new ArrayList<String>();
                for(int x = 1960 ; x < 2014;x++){
                    years.add(String.valueOf(x));
                }

                BarData data = new BarData(years ,  lineDataSets);



                chart.setData(data);
                chart.setDrawXLabels(false);
                chart.notifyDataSetChanged();

                /*ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
                ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
                valsComp1.add(new Entry(10,0));
                valsComp1.add(new Entry(15,1));
                valsComp1.add(new Entry(20,2));
                LineDataSet setComp1 = new LineDataSet(valsComp1, "uk");
                dataSets.add(setComp1);
                ArrayList<String> xVals = new ArrayList<String>();
                xVals.add("0"); xVals.add("1"); xVals.add("2");
                LineData data = new LineData(xVals, dataSets);
                chart.setData(data);*/
            }
        });



    }
}
