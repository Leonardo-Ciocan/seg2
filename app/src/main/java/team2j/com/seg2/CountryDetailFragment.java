package team2j.com.seg2;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
    Button compareButton;

    private YearSelectorDialog yearDialogTo;
    private YearSelectorDialog yearDialogFrom;

    boolean hide= false;
    boolean loaded = false;
    private ChartCardView test;
    private ChartCardView populationGraph;
    private ChartCardView co2Graph;
    private ChartCardView urbGraph;
    private ChartCardView lifeGraph;
    private View view;
    private ImageView flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.country_detail_fragment, container, false);
        //view.setBackgroundColor(Color.RED);

        fromButton = (Button) view.findViewById(R.id.fromButton);
        toButton = (Button) view.findViewById(R.id.toButton);
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
        name = (TextView) view.findViewById(R.id.countryName);
        population = (TextView) view.findViewById(R.id.population);
        life = (TextView) view.findViewById(R.id.life);
        co2label = (TextView) view.findViewById(R.id.co2label);
        urbanLabel = (TextView) view.findViewById(R.id.urbanlabel);


        populationGraph = (ChartCardView) view.findViewById(R.id.population_graph);
        co2Graph = (ChartCardView) view.findViewById(R.id.co2_graph);
        urbGraph = (ChartCardView) view.findViewById(R.id.urban_graph);
        lifeGraph = (ChartCardView) view.findViewById(R.id.life_graph);

        populationCard = (CardView) view.findViewById(R.id.populationCard);
        populationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(populationPoints, Core.POPULATION);

            }
        });

        lifeCard = (CardView) view.findViewById(R.id.lifeCard);
        lifeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(lifePoints, Core.LIFE);
            }
        });

        co2card = (CardView) view.findViewById(R.id.co2card);
        co2card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(co2Points, Core.CO2);
            }
        });

        urbanCard = (CardView) view.findViewById(R.id.urbancard);
        urbanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selected(urbanPoints, Core.URBAN);
            }
        });

        compareButton = (Button) view.findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , CompareActivity.class);
                getActivity().startActivity(intent);
            }
        });

        flag = (ImageView) view.findViewById(R.id.flag);

        final FrameLayout flagHolder = (FrameLayout)view.findViewById(R.id.flagHolder);
        flag.setPivotY(0);

        final ScrollView scrollView = (ScrollView)view.findViewById(R.id.scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                flag.setPivotX(flagHolder.getWidth()/2);
                int scrollY = scrollView.getScrollY();
                float h = flagHolder.getHeight();
                float alpha = 1 - scrollY / (h == 0 ? 0.01f:h);
                flag.setAlpha(alpha*2);
                //flag.setScaleX(alpha);
                flag.setScaleY(alpha);
            }
        });


        if(hide){
            fromButton.setVisibility(View.GONE);
            toButton.setVisibility(View.GONE);
            compareButton.setVisibility(View.GONE);
        }
        loaded = true;
        if(country!=null) setCountry(country);
        return view;
    }


    ArrayList<DataPoint> populationPoints;
    ArrayList<DataPoint> lifePoints;
    ArrayList<DataPoint> co2Points;
    ArrayList<DataPoint> urbanPoints;



    public Country country;
    public  void setCountry(Country c){
        country = c;



        //Core.currentCountry = c;
         String url = "http://api.worldbank.org/countries/" + c.getId() + "/indicators/SP.POP.TOTL?date="+Core.selectedFrom+":"+Core.selectedTo+"&format=json";

        DownloadTask task = new DownloadTask(0);
        task.setListener( new DownloadTask.DataDownloaded() {
            @Override
            public void downloaded(Object c) {
                final ArrayList<DataPoint> points = (ArrayList<DataPoint>)c;

                populationPoints = points;
                country.data.set(Core.POPULATION , points);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        population.setText((points.get(0)).getValue().toString());
                        name.setText(country.getName());
                        populationGraph.drawData(points);

                        flag = (ImageView) view.findViewById(R.id.flag);
                        String countryName = country.getName();
                        if(countryName.equals("United Kingdom")) countryName = "unitedkingdom";
                        int intId = getActivity().getResources().getIdentifier(countryName.toLowerCase(), "drawable", getActivity().getPackageName());

                        flag.setImageResource(intId);
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
                country.data.set(Core.LIFE, points);

                lifePoints = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        life.setText((points.get(0)).getValue().toString());
                        lifeGraph.drawData(points);

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
                country.data.set(Core.CO2, points);

                co2Points = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        co2label.setText((points.get(0)).getValue().toString());
                        co2Graph.drawData(points);

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
                country.data.set(Core.URBAN, points);

                urbanPoints = points;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        urbanLabel.setText((points.get(0)).getValue().toString() + "%");
                        urbGraph.drawData(points);

                    }
                });
            }
        });
        urbanTask.execute(urbalUrl);
    }

    /**
     * Triggered when the user presses an indicator
     * @param listener Sets the listener to be triggered
     */
    IndicatorSelected listener;
    public void setListener(IndicatorSelected listener){
        this.listener = listener;
    }
    public interface IndicatorSelected {
        void selected(ArrayList<DataPoint> points,int type);
    }

    public void hideButtons(){
        hide = true;
    }

}