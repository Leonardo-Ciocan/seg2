package team2j.com.seg2;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Locale;

public class CountriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.countries_fragment, container, false);
        ListView countriesList = (ListView)view.findViewById(R.id.countriesList);
        final EditText editText= (EditText) view.findViewById(R.id.et_search);
        final CountriesAdapter adapter=new CountriesAdapter(getActivity());
        countriesList.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(String.valueOf(s).toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.selected(Core.countries.get(position));
            }
        });
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