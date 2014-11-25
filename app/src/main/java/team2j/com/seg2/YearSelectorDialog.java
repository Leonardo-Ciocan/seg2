package team2j.com.seg2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class YearSelectorDialog extends DialogFragment {

    public ArrayList<String> Years = new ArrayList<String>();
    public String selectedYear = "";
    ArrayList<String> years = new ArrayList<String>();
    int selectedYearId = 0;

    Button linkedButton;

    boolean isOnLastYear;
    public YearSelectorDialog(){}
    public YearSelectorDialog(Button linkedButton , boolean isOnLastYear){
        this.linkedButton = linkedButton;
        for(int x = 1960 ; x <= 2014;x++){
            years.add(String.valueOf(x));
        }
        this.isOnLastYear = isOnLastYear;
        selectedYear = isOnLastYear ? "To 2014" : "From 1960";
        selectedYearId = isOnLastYear ? years.size()-1:0;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());



        final String[] arr = years.toArray(new String[years.size()]);

        builder.setTitle("Select a year").setSingleChoiceItems(arr ,selectedYearId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedYear = arr[which];
                selectedYearId = which;
                linkedButton.setText((isOnLastYear ? "To " : "From " ) + selectedYear);
                YearSelectorDialog.this.dismiss();
            }
        });

        return builder.create();
    }
}
