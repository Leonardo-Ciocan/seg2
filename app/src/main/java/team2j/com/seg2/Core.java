package team2j.com.seg2;

import com.github.mikephil.charting.charts.BarChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Core {
    // a list of data points is a line that represents a trend , so this is a list of lines
    public static ArrayList<DataSet> DataSets = new ArrayList<DataSet>();
    public static int pending_downloads = 0;
    public static BarChart chart;
    public static Country  currentCountry;
    public static CountryDetailFragment countryDetailFragment;

    public static Integer selectedFrom = 1960;
    public static Integer selectedTo = 2014;

    //this is triggered when all downloads are done
    public static OnDataSetsReady listener;
    public static void addOnDataSetsReady(OnDataSetsReady listener){
        Core.listener = listener;
    }
    public interface OnDataSetsReady{
        void ready();
    }

    public static ArrayList<Country> countries = new ArrayList<Country>(){
        {
            add(new Country("United Kingdom" , "gb"));
            add(new Country("Spain" , "es"));
            add(new Country("France" , "fr"));
            add(new Country("Iraq" , "irq"));
            add(new Country("Italy" , "it"));
        }
    };

    public static final int POPULATION_JSON = 1;

    public static ArrayList<DataPoint> parsePopulationJson(String json){
        ArrayList<DataPoint> history = new ArrayList<DataPoint>();
        JSONArray jObject = null;
        JSONArray object = null;
        String url = null;
        try {
            jObject = new JSONArray(json);
            object = jObject.getJSONArray(1);
            int x = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(object == null)
            return null;
        for(int x = 0; x < object.length();x++){
            try {
                JSONObject current = object.getJSONObject(x);
                history.add(new DataPoint(current.getInt("date"),current.getInt("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return history;
    }


}

