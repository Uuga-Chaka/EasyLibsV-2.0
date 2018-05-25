package icesi.dmi.easylibdos;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    Spinner personas, tipo;

    String[] persona = {"1", "2", "3", "4"};
    ArrayAdapter<String> adapter;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Goone
        return inflater.inflate(R.layout.fragment_filter, container, false);

    }

}
