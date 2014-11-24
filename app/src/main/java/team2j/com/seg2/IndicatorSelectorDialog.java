package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;

public class IndicatorSelectorDialog extends DialogFragment {

    final String[] indicators = {"Life expectancy", "GDP", "Population"};
    final String[] indicatorsID = {"SP.DYN.LE00.IN", "NY.GDP.MKTP.CD", "SP.POP.TOTL"};
    final boolean[] selectedItems = new boolean[3];

    public ArrayList<String> selectedIndicators = new ArrayList<String>();
    public ArrayList<String> selectedIDs = new ArrayList<String>();
    private SelectionChanged selectionChanged;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select some indicators")
                .setMultiChoiceItems(indicators, selectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                if(isChecked){
                                    selectedIDs.add(indicatorsID[item]);
                                    selectedIndicators.add(indicators[item]);
                                    selectedItems[item] = true;
                                }
                                else{
                                    if(selectedIDs.contains(indicatorsID[item]))selectedIDs.remove(indicatorsID[item]);
                                    if(selectedIndicators.contains(indicators[item]))selectedIndicators.remove(indicators[item]);
                                    selectedItems[item] = false;
                                }
                                selectionChanged.onSelectionChanged(TextUtils.join(",", selectedIndicators));
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
