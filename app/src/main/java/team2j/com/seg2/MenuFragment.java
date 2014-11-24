package team2j.com.seg2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mohammed on 11/24/2014.
 */
public class MenuFragment extends Fragment{

    Fragment fragment;
    FragmentTransaction transaction;

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.menu_frag,container,false);
       /* fragment = new TogetherFragment();
        transaction = getFragmentManager().beginTransaction().add( R.id.FragmentContainer,fragment );
        transaction.commit();

        Button btnTogetherFrag = (Button)view.findViewById(R.id.btnTogetherFrag);

        Button btnSeperateFrag = (Button)view.findViewById(R.id.btnSeperateFrag);

        Button btnMutliGraphesFrag = (Button)view.findViewById(R.id.btnMultiGraphesFrag);

        btnTogetherFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fragment = new TogetherFragment();
                    transaction = getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fragment);
                    transaction.commit();


            }
        });


        btnSeperateFrag.setOnClickListener(new View.OnClickListener(){

            @Override
        public void onClick(View v){
                fragment = new DataChartActivity();
                transaction = getFragmentManager().beginTransaction().replace( R.id.FragmentContainer,fragment );
                transaction.commit();
            }
        });

        btnMutliGraphesFrag.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                fragment = new MultiGraphesFragment();
                transaction = getFragmentManager().beginTransaction().replace( R.id.FragmentContainer,fragment );
                transaction.commit();
            }
        });
*/

        return view;
    }



}
