package team2j.com.seg2;

import java.util.ArrayList;

public class Country {
    String name;
    String id;
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
