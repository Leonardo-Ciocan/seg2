package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Let's the user select a year from 2060 to the current year
 */
public class YearSelectorDialog extends DialogFragment {

    public ArrayList<String> Years = new ArrayList<String>();
    public String selectedYear = "";
    ArrayList<String> years = new ArrayList<String>();
    int selectedYearId = 0;

    Button linkedButton;

    boolean isOnLastYear;

    public YearSelectorDialog() {
    }

    public YearSelectorDialog(Button linkedButton, boolean isOnLastYear) {
        this.linkedButton = linkedButton;
        for (int x = 1960; x <= Core.Year; x++) {
            years.add(String.valueOf(x));
        }
        this.isOnLastYear = isOnLastYear;


        selectedYearId = isOnLastYear ? years.size() - 1 : 0;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());


        final String[] arr = years.toArray(new String[years.size()]);

        builder.setTitle("Select a year").setSingleChoiceItems(arr, selectedYearId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedYear = arr[which];


                if (isOnLastYear) {
                  if(Core.selectedFrom<Integer.parseInt(selectedYear))  {Core.selectedTo = Integer.parseInt(selectedYear); setButtonText();}// String url = "http://api.worldbank.org/countries/" + Core.currentCountry.getId() + "/indicators/SP.POP.TOTL?date="+CountryDetailFragment.selectedFrom+":"+CountryDetailFragment.selectedTo+"&format=json";


                } else {
                    if (Core.selectedTo > Integer.parseInt(selectedYear)){
                        Core.selectedFrom = Integer.parseInt(selectedYear);
                    setButtonText();
                    }
                }
                selectedYearId = which;



               /* if(Core.selectedFrom != null && Core.selectedTo !=null){
                    BarData data = Core.chart.getData();
                }*/

                YearSelectorDialog.this.dismiss();
            }
        });

        return builder.create();
    }

    public void setButtonText() {


    linkedButton.setText((isOnLastYear?"To ":"From ")+selectedYear);

    Core.countryDetailFragment.setCountry(Core.currentCountry);
}

}
