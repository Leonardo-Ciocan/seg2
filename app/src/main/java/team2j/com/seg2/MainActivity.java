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

        final CountrySelectorDialog dialog = new CountrySelectorDialog();
        Button countryButton = (Button)findViewById(R.id.countryButton);
        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(),"diag");
            }
        });

        final IndicatorSelectorDialog dialog2 = new IndicatorSelectorDialog();
        Button indicatorButton = (Button)findViewById(R.id.indicatorButton);
        indicatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show(getFragmentManager(),"diag2");
            }
        });

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we must aggregate data from multiple urls
                ArrayList<String> urls = new ArrayList<String>();
                //if more than one country is selected then
                if(dialog.selectedIDs.size() > 1){
                    //we iterate through the selected countries and create links for each country paired with the first indicator selector
                    for(String country : dialog.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+country+"/indicators/"+dialog2.selectedIDs.get(0)+"?date=1960:2009&format=json");
                    }
                }
                else{
                    //else if multiple indicators are selected , we will pair the same country with multiple indicators
                    for(String indicator : dialog2.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+dialog.selectedIDs.get(0)+"/indicators/"+indicator+"?date=1960:2009&format=json");
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
