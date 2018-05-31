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

    Spinner s_personas, s_tipo;



    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        s_personas = rootView.findViewById(R.id.spin_people);
        s_tipo = rootView.findViewById(R.id.spin_tipo);

        String[] cantidad = new String[]{"1","2","3","4"};
        String[] items = new String[]{ "Office", "Home", "College", "Uncle's Home", "CoDebuggers"};
        ArrayAdapter<String> item = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> cantidades = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,cantidad);
        s_personas.setAdapter(item);
        s_tipo.setAdapter(cantidades);


        //Goone

        return  rootView;
    }

}
