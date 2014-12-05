package team2j.com.seg2;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class CompareActivity extends Activity {


    private CountryDetailFragment detailFragment;
    private CountryDetailFragment detailFragment1;
    private ComparasionChartFragment chartFragment;
    private FrameLayout chartHolder;
    private FrameLayout countryDetailFragmentHolder1;
    private FrameLayout countriesFragmentHolder;
    private FrameLayout countryDetailFragmentHolder;
    private CountriesFragment fragment;

    boolean shouldGoBack = true;

    int level = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_activity);
        getActionBar().hide();

        /*CountryDetailFragment fragment = new CountryDetailFragment();
        FrameLayout detailHolder1 = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);
        getFragmentManager().beginTransaction().add(detailHolder1.getId() , fragment).commit();*/


        countriesFragmentHolder = (FrameLayout)findViewById(R.id.secondCountryHolder);

        countryDetailFragmentHolder1 = (FrameLayout)findViewById(R.id.countryDetailFragmentHolderFirst);


        detailFragment1 = (CountryDetailFragment)getFragmentManager().findFragmentByTag("detail1");
        if(detailFragment1==null) {
            detailFragment1 = new CountryDetailFragment();
            getFragmentManager().beginTransaction().add(countryDetailFragmentHolder1.getId(), detailFragment1, "detail1").commit();
        }


        detailFragment1.setCountry(Core.currentCountry);
        detailFragment1.hideButtons();

        countryDetailFragmentHolder = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);

        detailFragment = (CountryDetailFragment)getFragmentManager().findFragmentByTag("detail");
        if(detailFragment==null) {
            detailFragment = new CountryDetailFragment();
            getFragmentManager().beginTransaction().add(countriesFragmentHolder.getId(), detailFragment,"detail").commit();
        }
        detailFragment.hideButtons();

        chartHolder = (FrameLayout)findViewById(R.id.chartHolder);

        chartFragment =  (ComparasionChartFragment)getFragmentManager().findFragmentByTag("compare_chart");
        if(chartFragment==null) {
            chartFragment = new ComparasionChartFragment();
            getFragmentManager().beginTransaction().add(chartHolder.getId(), chartFragment , "compare_chart").commit();
        }


        getFragmentManager().beginTransaction().hide(chartFragment).commit();
        getFragmentManager().beginTransaction().hide(detailFragment).commit();



        fragment = (CountriesFragment)getFragmentManager().findFragmentByTag("countries_");
        if(fragment == null){
           fragment = new CountriesFragment();
            getFragmentManager().beginTransaction().add(countriesFragmentHolder.getId() , fragment,"countries_").commit();
        }


        fragment.setListener(new CountriesFragment.CountrySelected() {
            @Override
            public void selected(Country c) {
                detailFragment.setCountry(c);
                shouldGoBack = false;
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).hide(chartFragment).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).hide(fragment).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).show(detailFragment).commit();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fragment.editText.getWindowToken(), 0);
            }
        });

        detailFragment.setListener(new CountryDetailFragment.IndicatorSelected() {
            @Override
            public void selected(ArrayList<DataPoint> points, int type) {
                level = 1;
                if(!getResources().getBoolean(R.bool.isTablet)) {
                    (findViewById(R.id.firstCollumn)).setVisibility(View.GONE);
                }

                if(type == 0){
                    chartFragment.chart.setDescription("CO2");
                }
                else if (type == 1) {
                    chartFragment.chart.setDescription("Life expectancy");
                }
                else if (type == 2) {
                    chartFragment.chart.setDescription("Population");
                }
                else if (type == 3) {
                    chartFragment.chart.setDescription("Urban population");
                }


                chartHolder.setVisibility(View.VISIBLE);
                chartFragment.countryName1 = detailFragment1.country.getName();
                chartFragment.countryName2 = detailFragment.country.getName();

                chartFragment.renderData(Core.currentCountry.data.get(type), points);
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).show(chartFragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!getResources().getBoolean(R.bool.isTablet)){
            if(level ==1) {
                chartHolder.setVisibility(View.GONE);
                (findViewById(R.id.firstCollumn)).setVisibility(View.VISIBLE);
                level = 0;
                return;
            }

        }
        if (!shouldGoBack) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).show(fragment).commit();
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show, R.anim.hide).hide(detailFragment).commit();
            shouldGoBack = true;
        } else {
            super.onBackPressed();
        }
    }
}
