package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;

public class CountrySelectorDialog extends DialogFragment {

    //the country name shown to the user and equivalent id
    final String[] countries = {"United Kingdom", "Spain", "France"};
    final String[] countriesID = {"gb", "es", "fr"};
    final boolean[] selectedItems = new boolean[3];

    public ArrayList<String> selectedCountries = new ArrayList<String>();
    public ArrayList<String> selectedIDs = new ArrayList<String>();
    SelectionChanged selectionChanged;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select a country")
                .setMultiChoiceItems(countries, selectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                //add and remove ids on selected/unselected

                                if(isChecked){
                                    selectedIDs.add(countriesID[item]);
                                    selectedCountries.add(countries[item]);
                                    selectedItems[item] = true;
                                }
                                else{
                                    if(selectedIDs.contains(countriesID[item]))selectedIDs.remove(countriesID[item]);
                                    if(selectedCountries.contains(countries[item]))selectedCountries.remove(countries[item]);
                                    selectedItems[item] = false;
                                }

                                selectionChanged.onSelectionChanged(TextUtils.join(",",selectedCountries));
                            }
                        })
        .setPositiveButton("Done" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void setSelectionChangedListener(SelectionChanged s){
        selectionChanged = s;
    }


}
