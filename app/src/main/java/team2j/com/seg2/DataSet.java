package team2j.com.seg2;

import java.util.ArrayList;

public class DataSet {
    String name;
    ArrayList<DataPoint> points;

    public DataSet(String name, ArrayList<DataPoint> points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DataPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<DataPoint> points) {
        this.points = points;
    }
}
