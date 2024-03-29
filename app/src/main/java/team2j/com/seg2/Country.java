package team2j.com.seg2;

import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * A country containing a name and id
 */
public class Country {
    String name;
    String id;
    /**
     * Array to hold multiple indicator data for caching
     */
    public ArrayList<ArrayList<DataPoint>> data = new ArrayList<ArrayList<DataPoint>>(){
        {
            add(new ArrayList<DataPoint>());
            add(new ArrayList<DataPoint>());
            add(new ArrayList<DataPoint>());
            add(new ArrayList<DataPoint>());
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Country(String name, String id) {

        this.name = name;
        this.id = id;
    }


    @Override
    public String toString() {
        return name;
    }


}
