package icesi.dmi.easylibdos;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    Spinner s_personas;
    EditText hInicio, hFin;

    Button btn_buscar;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Universidades").child("Icesi");
    DatabaseReference userdb;

    Coordenada coordenada;
    User user;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btn_buscar = rootView.findViewById(R.id.btn_find);
        s_personas = rootView.findViewById(R.id.spin_people);
        hInicio = rootView.findViewById(R.id.et_inicio);
        hFin = rootView.findViewById(R.id.et_fin);

        String[] items = new String[]{"Tablero", "Ethernet", "Marcador", "Mesa", "Enchufe"};
        ArrayAdapter<String> item = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        s_personas.setAdapter(item);

        userdb = db.getReference().child("Bibliotech").child("Users").child("Icesi").child(currentUser.getUid());


        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = GregorianCalendar.getInstance();
                Date date = new Date();
                calendar.setTime(date);

                Query query = ref.orderByChild("filter").equalTo(s_personas.getSelectedItem().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (hInicio.getText().toString().isEmpty() || hFin.getText().toString().isEmpty()) {
                            return;
                        }
                        int inicio = Integer.parseInt(hInicio.getText().toString());
                        int fin = Integer.parseInt(hFin.getText().toString());

                        if(fin < calendar.get(Calendar.HOUR_OF_DAY)){
                            Toast.makeText(getActivity(),"Esta hora ya pasó",Toast.LENGTH_LONG).show();
                            return;
                        }


                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            Toast.makeText(getActivity(), s_personas.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                            Coordenada c = child.getValue(Coordenada.class);
                            if (!user.hasBooking) {
                                if (!c.hasReserva) {
                                    c.hasReserva = true;
                                    user.hasBooking = true;
                                    user.inicio = inicio;
                                    user.fin = fin;
                                    c.inicio = inicio;
                                    c.fin = fin;
                                    c.state = 1;
                                    userdb.setValue(user);
                                    ref.child(c.ids).setValue(c);
                                    break;
                                } else {
                                    Toast.makeText(getActivity(), "Este lugar ya tiene una reserva", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Ya tienes una reserva", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return rootView;
    }
}
