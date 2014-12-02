package team2j.com.seg2;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class CompareActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_activity);
        getActionBar().hide();

        /*CountryDetailFragment fragment = new CountryDetailFragment();
        FrameLayout detailHolder1 = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);
        getFragmentManager().beginTransaction().add(detailHolder1.getId() , fragment).commit();*/


        final CountryDetailFragment detailFragment = new CountryDetailFragment();


        FrameLayout countryDetailFragmentHolder = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);

        getFragmentManager().beginTransaction().add(countryDetailFragmentHolder.getId(), detailFragment).commit();

        final DataChartFragment chartFragment = new DataChartFragment();


        final FrameLayout chartHolder = (FrameLayout)findViewById(R.id.chartHolder);

        getFragmentManager().beginTransaction().add(chartHolder.getId(), chartFragment).commit();





        CountriesFragment fragment = new CountriesFragment();

        FrameLayout countriesFragmentHolder = (FrameLayout)findViewById(R.id.secondCountryHolder);
        getFragmentManager().beginTransaction().add(countriesFragmentHolder.getId() , fragment).commit();

        fragment.setListener(new CountriesFragment.CountrySelected() {
            @Override
            public void selected(Country c) {
                detailFragment.setCountry(c);
            }
        });

        detailFragment.setListener(new CountryDetailFragment.IndicatorSelected() {
            @Override
            public void selected(ArrayList<DataPoint> points ,int type) {
                chartFragment.renderData(Core.currentCountry.data.get(type) , points);
            }
        });


    }
}
