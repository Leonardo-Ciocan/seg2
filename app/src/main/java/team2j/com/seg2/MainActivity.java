package team2j.com.seg2;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main activity
 */
public class MainActivity extends Activity {


    int level = 0;
    private FrameLayout countriesFragmentHolder;
    private FrameLayout chartHolder;
    private FrameLayout countryDetailFragmentHolder;
    private DataChartFragment chartFragment;
    private CountryDetailFragment detailFragment;
    private CountriesFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getActionBar().hide();


        countriesFragmentHolder = (FrameLayout)findViewById(R.id.countriesFragmentHolder);

        fragment = (CountriesFragment)getFragmentManager().findFragmentByTag("COUNTRIES");
        if(fragment == null){
            fragment = new CountriesFragment();
            getFragmentManager().beginTransaction().add(countriesFragmentHolder.getId() , fragment, "COUNTRIES").commit();

        }

        countryDetailFragmentHolder = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);

        detailFragment =(CountryDetailFragment)getFragmentManager().findFragmentByTag("DETAIL");
        if(detailFragment == null){
            detailFragment = new CountryDetailFragment();
            getFragmentManager().beginTransaction().add(countryDetailFragmentHolder.getId(), detailFragment , "DETAIL").commit();
            getFragmentManager().beginTransaction().hide(detailFragment).commit();
        }
        Core.countryDetailFragment = detailFragment;



        chartHolder = (FrameLayout)findViewById(R.id.chartHolder);

        chartFragment = (DataChartFragment)getFragmentManager().findFragmentByTag("CHART");
        if(chartFragment == null){
            chartFragment = new DataChartFragment();
            getFragmentManager().beginTransaction().add(chartHolder.getId(), chartFragment , "CHART").commit();
            getFragmentManager().beginTransaction().hide(chartFragment).commit();

        }









        fragment.setListener(new CountriesFragment.CountrySelected() {
            @Override
            public void selected(Country c) {
                level = 1;
                if (!getResources().getBoolean(R.bool.isTablet)) {
                    countriesFragmentHolder.setVisibility(View.GONE);
                    countryDetailFragmentHolder.setVisibility(View.VISIBLE);
                }
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).hide(chartFragment).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).show(detailFragment).commit();
                Core.currentCountry = c;
                detailFragment.setCountry(c);

                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fragment.editText.getWindowToken(), 0);

            }
        });





        detailFragment.setListener(new CountryDetailFragment.IndicatorSelected() {
            @Override
            public void selected(ArrayList<DataPoint> points, int type) {
                level = 2;
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).show(chartFragment).commit();
                if (!getResources().getBoolean(R.bool.isTablet)) {
                    chartHolder.setVisibility(View.VISIBLE);
                    countryDetailFragmentHolder.setVisibility(View.GONE);
                }

                if (type == 0) {
                    chartFragment.chart.setDescription("CO2");
                } else if (type == 1) {
                    chartFragment.chart.setDescription("Life expectancy");
                } else if (type == 2) {
                    chartFragment.chart.setDescription("Population");
                } else if (type == 3) {
                    chartFragment.chart.setDescription("Urban population");
                }

                if (type == 0) {
                    chartFragment.lineChart.setDescription("CO2");
                } else if (type == 1) {
                    chartFragment.lineChart.setDescription("Life expectancy");
                } else if (type == 2) {
                    chartFragment.lineChart.setDescription("Population");
                } else if (type == 3) {
                    chartFragment.lineChart.setDescription("Urban population");
                }

                chartFragment.renderData(points);


            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds indicators to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!getResources().getBoolean(R.bool.isTablet) && level > 0){
            if(level == 2) {
                chartHolder.setVisibility(View.GONE);
                countryDetailFragmentHolder.setVisibility(View.VISIBLE);

            }
            if(level == 1) {
                countryDetailFragmentHolder.setVisibility(View.GONE);
                countriesFragmentHolder.setVisibility(View.VISIBLE);
            }
            level--;
        }
        else {
            super.onBackPressed();
        }
    }
}
