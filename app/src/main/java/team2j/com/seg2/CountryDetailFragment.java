package team2j.com.seg2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailFragment extends Fragment {

    TextView name;
    TextView population;
    TextView co2label;
    TextView urbanLabel;

    TextView life;
    CardView populationCard;
    CardView lifeCard;
    CardView co2card;
    CardView urbanCard;

    private Button fromButton;
    private Button toButton;

    private YearSelectorDialog yearDialogTo;
    private YearSelectorDialog yearDialogFrom;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.country_detail_fragment, container, false);


        fromButton = (Button)view.findViewById(R.id.fromButton);
        toButton = (Button)view.findViewById(R.id.toButton);
        yearDialogTo = new YearSelectorDialog(toButton,true);
        yearDialogFrom = new YearSelectorDialog(fromButton,false);
        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialogFrom.show(getFragmentManager(), "from");
                setCountry(Core.currentCountry);

            }
        });

        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialogTo.show(getFragmentManager(), "to");
                  setCountry(Core.currentCountry);


            }
        });




        name = (TextView)view.findViewById(R.id.countryName);
        population = (TextView)view.findViewById(R.id.population);
        life = (TextView)view.findViewById(R.id.life);
        co2label = (TextView)view.findViewById(R.id.co2label);
        urbanLabel = (TextView)view.findViewById(R.id.urbanlabel);

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

        co2card = (CardView)view.findViewById(R.id.co2card);
        co2card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(co2Points);
            }
        });

        urbanCard = (CardView)view.findViewById(R.id.urbancard);
        urbanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(urbanPoints);
            }
        });


        return view;
    }


    ArrayList<DataPoint> populationPoints;
    ArrayList<DataPoint> lifePoints;
    ArrayList<DataPoint> co2Points;
    ArrayList<DataPoint> urbanPoints;





    public  void setCountry(Country c){
        name.setText(c.getName());
        Core.currentCountry = c;
         String url = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.POP.TOTL?date="+Core.selectedFrom+":"+Core.selectedTo+"&format=json";
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

         String lifeUrl =  "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.DYN.LE00.IN?date="+Core.selectedFrom+":"+Core.selectedTo+"&format=json";
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


        final String co2Url = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/EN.ATM.CO2E.KT?date="+Core.selectedFrom+":"+Core.selectedTo+"&format=json";
        DownloadTask co2task = new DownloadTask(0);
        co2task.setListener( new DownloadTask.DataDownloaded() {
            @Override
            public void downloaded(Object c) {
                final ArrayList<DataPoint> points = (ArrayList<DataPoint>)c;
                co2Points = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        co2label.setText((points.get(points.size() - 1)).getValue().toString());
                    }
                });
            }
        });
        co2task.execute(co2Url);

        final String urbalUrl = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.URB.TOTL.IN.ZS?date="+Core.selectedFrom+":"+Core.selectedTo+"&format=json";
        DownloadTask urbanTask = new DownloadTask(0);
        urbanTask.setListener( new DownloadTask.DataDownloaded() {
            @Override
            public void downloaded(Object c) {
                final ArrayList<DataPoint> points = (ArrayList<DataPoint>)c;
                urbanPoints = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        urbanLabel.setText((points.get(points.size() - 1)).getValue().toString() + "%");
                    }
                });
            }
        });
        urbanTask.execute(urbalUrl);
    }

    IndicatorSelected listener;
    public void setListener(IndicatorSelected listener){
        this.listener = listener;
    }
    public interface IndicatorSelected {
        void selected(ArrayList<DataPoint> points);
    }

    YearSelected yearListener;
    public void setListener(YearSelected listener){
        this.yearListener = listener;
    }
    public interface YearSelected{
        void yearSelectedUpdate();
    }

}