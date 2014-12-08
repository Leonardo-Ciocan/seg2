package team2j.com.seg2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Used to populate the countries listview
 */
public class CountriesAdapter extends ArrayAdapter<Country> {

    ArrayList<Country> countries = new ArrayList<Country>();

    ArrayList<Country> original = new ArrayList<Country>();
    Context context;
    public CountriesAdapter(Context context) {
        super(context, R.layout.country_row, new ArrayList<Country>(Core.countries));
        //addAll(Core.countries);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.country_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.countryName);
        textView.setText(getItem(position).getName());
        ImageView flag = (ImageView) rowView.findViewById(R.id.flag);


        String countryName = getItem(position).getName().toLowerCase();
        int intId = context.getResources().getIdentifier(countryName, "drawable", context.getPackageName());

        flag.setImageResource(intId);
        return rowView;
    }


}
