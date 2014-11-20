package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class IndicatorSelectorDialog extends DialogFragment {

    final String[] indicators = {"Life expectancy", "GDP", "Population"};
    final String[] indicatorsID = {"Life expectancy", "GDP", "SP.POP.TOTL"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Select some indicators")
                .setMultiChoiceItems(indicators, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {

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
