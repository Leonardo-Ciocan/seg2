package team2j.com.seg2;

import java.util.ArrayList;
import java.util.HashMap;

public class Core {
    public static ArrayList<ArrayList<DataPoint>> DataSets = new ArrayList<ArrayList<DataPoint>>();
    public static int pending_downloads = 0;

    public static OnDataSetsReady listener;
    public static void addOnDataSetsReady(OnDataSetsReady listener){
        Core.listener = listener;
    }
    public interface OnDataSetsReady{
        void ready();
    }
}

