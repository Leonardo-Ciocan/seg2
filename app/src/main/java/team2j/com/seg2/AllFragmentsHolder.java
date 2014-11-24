package team2j.com.seg2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class AllFragmentsHolder extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_fragments_holder);

      if(savedInstanceState == null){
            Fragment fragment = new MenuFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction().add(R.id.menuFragmentContainer, fragment);
            transaction.commit();

         // getFragmentManager().beginTransaction().add(R.id.menuFragmentContainer, new MenuFragment()).commit();


        }



    }



}
