package team2j.com.seg2;

import android.app.Activity;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();



        CountriesFragment fragment = new CountriesFragment();

        FrameLayout countriesFragmentHolder = (FrameLayout)findViewById(R.id.countriesFragmentHolder);
        getFragmentManager().beginTransaction().add(countriesFragmentHolder.getId() , fragment).commit();


        final CountryDetailFragment detailFragment = new CountryDetailFragment();
        FrameLayout countryDetailFragmentHolder = (FrameLayout)findViewById(R.id.countryDetailFragmentHolder);
        getFragmentManager().beginTransaction().add(countryDetailFragmentHolder.getId() , detailFragment).commit();


        final DataChartFragment chartFragment = new DataChartFragment();
        FrameLayout chartHolder = (FrameLayout)findViewById(R.id.chartHolder);
        getFragmentManager().beginTransaction().add(chartHolder.getId() , chartFragment).commit();

        getFragmentManager().beginTransaction().hide(chartFragment).commit();
        getFragmentManager().beginTransaction().hide(detailFragment).commit();


        fragment.setListener(new CountriesFragment.CountrySelected() {
            @Override
            public void selected(Country c) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show,R.anim.hide).hide(chartFragment).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show,R.anim.hide).show(detailFragment).commit();

                detailFragment.setCountry(c);
            }
        });



        detailFragment.setListener(new CountryDetailFragment.IndicatorSelected() {
            @Override
            public void selected(ArrayList<DataPoint> points) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.show,R.anim.hide).show(chartFragment).commit();
                chartFragment.renderData(points);
            }
        });




    }

    public boolean isValidInput(){
        /*if(countrySelectorDialog.selectedIDs.size() == 0 || indicatorSelectorDialog.selectedIDs.size() ==0)
            return false;
        if(Integer.parseInt(yearDialogTo.selectedYear) < Integer.parseInt(yearDialogFrom.selectedYear))
            return false;
        return true;*/
        return true;
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
}
