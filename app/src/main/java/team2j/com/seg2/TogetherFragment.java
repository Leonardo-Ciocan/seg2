package team2j.com.seg2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TogetherFragment extends Fragment {

   public TogetherFragment(){

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.together_frag,container,false);



        return view;
    }

}
