package team2j.com.seg2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button countryButton = (Button)findViewById(R.id.countryButton);
        Button indicatorButton = (Button)findViewById(R.id.indicatorButton);
        Button searchButton = (Button)findViewById(R.id.searchButton);
        Button fromButton = (Button)findViewById(R.id.yearFromButton);
        Button toButton = (Button)findViewById(R.id.yearToButton);

        final CountrySelectorDialog countrySelectorDialog = new CountrySelectorDialog();
        final IndicatorSelectorDialog indicatorSelectorDialog = new IndicatorSelectorDialog();
        final YearSelectorDialog yearDialogTo = new YearSelectorDialog(toButton);
        final YearSelectorDialog yearDialogFrom = new YearSelectorDialog(fromButton);



        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrySelectorDialog.show(getFragmentManager(), "diag");
            }
        });

        indicatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorSelectorDialog.show(getFragmentManager(), "diag2");
            }
        });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we must aggregate data from multiple urls
                ArrayList<String> urls = new ArrayList<String>();
                //if more than one country is selected then
                if(countrySelectorDialog.selectedIDs.size() > 1){
                    //we iterate through the selected countries and create links for each country paired with the first indicator selector
                    for(String country : countrySelectorDialog.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+country+"/indicators/"+indicatorSelectorDialog.selectedIDs.get(0)+"?date="+yearDialogFrom.selectedYear+":"+yearDialogTo.selectedYear+"&format=json");
                    }
                }
                else{
                    //else if multiple indicators are selected , we will pair the same country with multiple indicators
                    for(String indicator : indicatorSelectorDialog.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+countrySelectorDialog.selectedIDs.get(0)+"/indicators/"+indicator+"?date="+yearDialogFrom.selectedYear+":"+yearDialogTo.selectedYear+"&format=json");
                    }
                }

                //the numbers of urls is stored to know when all downloads are done
                Core.pending_downloads = urls.size();
                for(String url : urls){
                    new DownloadTask(url).execute(url);
                }

                Intent intent = new Intent(MainActivity.this , DataChartActivity.class);
                startActivity(intent);
            }
        });

        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialogFrom.show(getFragmentManager(), "from");
            }
        });

        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialogTo.show(getFragmentManager(), "to");
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
}
