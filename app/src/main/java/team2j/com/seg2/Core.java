package team2j.com.seg2;

import com.github.mikephil.charting.charts.BarChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Core {
    /**
     * The country the user selected
     */
    public static Country  currentCountry;
    /**
     * The fragment on the main activity
     */
    public static CountryDetailFragment countryDetailFragment;

    /**
     * The year filter as set by the user
     */
    public static Integer selectedFrom = 1960;
    /**
     * The year filter as set by the user
     */
    public static Integer selectedTo = 2014;


    /**
     * Identifies CO2
     */
    public static int CO2 = 0;
    /**
     * Identifies life expectancy
     */
    public static int LIFE = 1;
    /**
     * Identifies population
     */
    public static int POPULATION = 2;
    /**
     * Identifies urban population percentage
     */
    public static int URBAN = 3;

    /**
     * The current year
     */
    public static int Year;

    static {
        Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
       selectedTo = Year;
    }


    /**
     * All the countries and their ids
     */
    public static ArrayList<Country> countries = new ArrayList<Country>(){
        {
            add(new Country("United Kingdom" , "gb"));
            add(new Country("Spain" , "es"));
            add(new Country("France" , "fr"));
            add(new Country("Italy" , "it"));
            add(new Country("Germany","de"));
            add(new Country("Russia","ru"));
            add(new Country("Ukraine","ua"));
            add(new Country("Finland","fi"));
            add(new Country("Sweden","se"));
            add(new Country("Norway","no"));
            add(new Country("Denmark","dk"));
            add(new Country("Latvia","lv"));
            add(new Country("Lithuania","lt"));
            add(new Country("Belarus","by"));
            add(new Country("Moldova","md"));
            add(new Country("Poland","pl"));
            add(new Country("Czech","cz"));
            add(new Country("Slovakia","sk"));
            add(new Country("Hungary","hu"));
            add(new Country("Austria","at"));
            add(new Country("Switzerland","ch"));
           // add(new Country("Liechtenstein","li"));
            add(new Country("Ireland","ie"));
            add(new Country("Holland","nl"));
            add(new Country("Belgium","be"));
            add(new Country("Luxembourg","lu"));
            //add(new Country("Monaco","mc"));
            add(new Country("Romania","ro"));
            add(new Country("Bulgaria","bg"));
            add(new Country("Macedonia","mk"));
            add(new Country("Albania","al"));
            add(new Country("Greece","gr"));
            add(new Country("Slovenia","si"));
            add(new Country("Croatia","hr"));
            add(new Country("Bosnia","ba"));
            //add(new Country("SanMarino","sm"));
            add(new Country("Turkey","tr"));
            add(new Country("Portugal","pt"));
            add(new Country("Andorra","ad"));
            add(new Country("Serbia","rs"));
        }
    };


    /**
     * Parses the data
     * @param json The json returned by the downloader
     * @see team2j.com.seg2.DownloadTask
     * @return The values and years in an array of {@link team2j.com.seg2.DataPoint}
     */
    public static ArrayList<DataPoint> parse(String json){
        ArrayList<DataPoint> history = new ArrayList<DataPoint>();
        JSONArray jObject;
        JSONArray object = null;
        try {
            jObject = new JSONArray(json);
            object = jObject.getJSONArray(1);
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

