package team2j.com.seg2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Random;


public class DataChartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datachart);


        final LineChart chart = (LineChart) findViewById(R.id.chart);

        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.brandColor)));
        getActionBar().setTitle(getIntent().getStringExtra("title"));
        chart.setDescription(getIntent().getStringExtra("description"));
        //triggered when the last json download finished converting
        Core.addOnDataSetsReady(new Core.OnDataSetsReady(){

            @Override
            public void ready() {

                //each DataPoint is converted to a BarEntry
                ArrayList<ArrayList<Entry>> values = new ArrayList<ArrayList<Entry>>();

                for(DataSet pairs : Core.DataSets){
                    //data must be sorted by year
                    Collections.sort(pairs.getPoints(), new Comparator<DataPoint>() {
                        @Override
                        public int compare(DataPoint lhs, DataPoint rhs) {
                            return lhs.getYear() > rhs.getYear() ? 1 : 0;
                        }
                    });
                    ArrayList<Entry> entries = new ArrayList<Entry>();
                    for(DataPoint point : pairs.getPoints()){
                        entries.add(new BarEntry(point.getValue(),point.getYear() - 1960));
                    }
                    values.add(entries);
                }

                Random rnd = new Random();
                boolean first = true;
                int n = 0;
                //this needs to be updated :|
                ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
                for(ArrayList<Entry> entryArrayList : values){
                    LineDataSet dataSet = new LineDataSet(entryArrayList , Core.DataSets.get(n).getName());
                    n++;
                    dataSet.setColor(first ? getResources().getColor(R.color.brandColor) : Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)) );
                    dataSet.setHighLightColor(Color.BLACK);
                    dataSet.setCircleColor(dataSet.getColor());
                    first = false;
                    dataSet.setLineWidth(3);

                    dataSet.setCircleSize(1f);
                    //dataSet.setDrawCubic(true);
                   // dataSet.setCubicIntensity(1);

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

                //this handler was called from the network thread so we must go back to the ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //chart.invalidate();
                        chart.animateY(3000);
                    }
                });

            }
        });



    }
}
