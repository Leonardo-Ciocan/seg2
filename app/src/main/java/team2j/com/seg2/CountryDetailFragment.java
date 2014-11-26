package team2j.com.seg2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailFragment extends Fragment {

    TextView name;
    TextView population;

    TextView life;
    CardView populationCard;
    CardView lifeCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.country_detail_fragment, container, false);
        name = (TextView)view.findViewById(R.id.countryName);
        population = (TextView)view.findViewById(R.id.population);
        life = (TextView)view.findViewById(R.id.life);

        populationCard = (CardView)view.findViewById(R.id.populationCard);
        populationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(populationPoints);
            }
        });

        lifeCard = (CardView)view.findViewById(R.id.lifeCard);
        lifeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(lifePoints);
            }
        });


        return view;
    }

    ArrayList<DataPoint> populationPoints;
    ArrayList<DataPoint> lifePoints;

    public void setCountry(Country c){
        name.setText(c.getName());
        final String url = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.POP.TOTL?date=1960:2014&format=json";
        DownloadTask task = new DownloadTask(0);
        task.setListener( new DownloadTask.DataDownloaded() {
            @Override
            public void downloaded(Object c) {
                final ArrayList<DataPoint> points = (ArrayList<DataPoint>)c;
                populationPoints = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        population.setText((points.get(points.size() - 1)).getValue().toString());
                    }
                });
            }
        });
        task.execute(url);

        final String lifeUrl = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.DYN.LE00.IN?date=1960:2014&format=json";
        DownloadTask lifeTask = new DownloadTask(0);
        lifeTask.setListener( new DownloadTask.DataDownloaded() {
            @Override
            public void downloaded(Object c) {
                final ArrayList<DataPoint> points = (ArrayList<DataPoint>)c;
                lifePoints = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        life.setText((points.get(points.size() - 1)).getValue().toString());
                    }
                });
            }
        });
        lifeTask.execute(lifeUrl);
    }

    IndicatorSelected listener;
    public void setListener(IndicatorSelected listener){
        this.listener = listener;
    }
    public interface IndicatorSelected {
        void selected(ArrayList<DataPoint> points);
    }
}