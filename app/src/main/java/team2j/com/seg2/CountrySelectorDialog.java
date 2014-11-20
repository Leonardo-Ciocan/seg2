package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

public class CountrySelectorDialog extends DialogFragment {

    final String[] countries = {"United Kingdom", "Spain", "France"};
    final String[] countriesID = {"gb", "es", "fr"};

    public ArrayList<String> selectedIDs = new ArrayList<String>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select a country")
                .setMultiChoiceItems(countries, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                if(isChecked){
                                    selectedIDs.add(countriesID[item]);
                                }
                                else{
                                    if(selectedIDs.contains(countriesID[item]))selectedIDs.remove(countriesID[item]);
                                }
                            }
                        })
        .setPositiveButton("Done" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
