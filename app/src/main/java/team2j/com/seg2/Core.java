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
    //public static BarChart chart;
    public static Country  currentCountry;
    public static CountryDetailFragment countryDetailFragment;

    public static Integer selectedFrom = 1960;
    public static Integer selectedTo = 2014;

    public Country countryToCompare;

    public static int CO2 = 0;
    public static int LIFE = 1;
    public static int POPULATION = 2;
    public static int URBAN = 3;

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
            add(new Country("Bosnia-Herzegovina","ba"));
            //add(new Country("SanMarino","sm"));
            add(new Country("Turkey","tr"));
            add(new Country("Portugal","pt"));
            add(new Country("Andorra","ad"));
            add(new Country("Serbia","rs"));






//            add(new Country("Vietnam","vn"));
            add(new Country("China","cn"));
//            add(new Country("United States","us"));
//            add(new Country("Japan","jp"));
//            add(new Country("Korea","kr"));
//            add(new Country("Singapore","sg"));
//            add(new Country("Bangladesh","bd"));
//            add(new Country("Iraq" , "irq"));




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

