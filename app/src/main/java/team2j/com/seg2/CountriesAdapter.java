package team2j.com.seg2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountriesAdapter extends ArrayAdapter<Country> {

    Context context;
    public CountriesAdapter(Context context) {
        super(context, R.layout.country_row, Core.countries);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.country_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.countryName);
        textView.setText(Core.countries.get(position).getName());

        return rowView;
    }
}
