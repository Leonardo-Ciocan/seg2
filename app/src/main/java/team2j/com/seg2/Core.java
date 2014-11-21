package team2j.com.seg2;

import java.util.ArrayList;
import java.util.HashMap;

public class Core {
    // a list of data points is a line that represents a trend , so this is a list of lines
    public static ArrayList<ArrayList<DataPoint>> DataSets = new ArrayList<ArrayList<DataPoint>>();
    public static int pending_downloads = 0;

    //this is triggered when all downloads are done
    public static OnDataSetsReady listener;
    public static void addOnDataSetsReady(OnDataSetsReady listener){
        Core.listener = listener;
    }
    public interface OnDataSetsReady{
        void ready();
    }
}

