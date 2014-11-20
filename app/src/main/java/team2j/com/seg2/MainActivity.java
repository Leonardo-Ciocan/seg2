package team2j.com.seg2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
                //String URL = "http://api.worldbank.org/countries/""/indicators/SP.POP.TOTL?date=1960:2009&format=json";
                ArrayList<String> urls = new ArrayList<String>();
                if(dialog.selectedIDs.size() > 1){
                    for(String country : dialog.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+country+"/indicators/"+dialog2.selectedIDs.get(0)+"?date=1960:2009&format=json");
                    }
                }
                else{
                    for(String indicator : dialog2.selectedIDs){
                        urls.add("http://api.worldbank.org/countries/"+dialog.selectedIDs.get(0)+"/indicators/"+indicator+"?date=1960:2009&format=json");
                    }
                }
                int x = 0;
            }
        });

         String URL = "http://api.worldbank.org/countries/GB/indicators/SP.POP.TOTL?date=1960:2009&format=json";
        new DownloadTask(URL).execute(URL);

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
