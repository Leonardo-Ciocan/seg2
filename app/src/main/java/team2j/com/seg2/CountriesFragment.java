package team2j.com.seg2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class CountriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.countries_fragment, container, false);
        ListView countriesList = (ListView)view.findViewById(R.id.countriesList);
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.selected(Core.countries.get(position));
            }
        });
        countriesList.setAdapter(new CountriesAdapter(getActivity()));
        return view;
    }


    CountrySelected listener;
    public void setListener(CountrySelected listener){
        this.listener = listener;
    }
    public interface CountrySelected {
        void selected(Country c);
    }
}