package icesi.dmi.easylibdos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements View.OnTouchListener {

    int[] pos;
    private Mapa mapa;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Universidades").child("Icesi");

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapa = (Mapa) root.findViewById(R.id.mapita);


        int[] location = new int[2];
        container.getLocationInWindow(location);


        mapa.setOnTouchListener(this);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Coordenada coordenada = child.getValue(Coordenada.class);
                    int x = coordenada.getX();
                    int y = coordenada.getY();
                    String id = coordenada.getId();
                    int s = coordenada.getState();
                    mapa.addSilla(x, y, id, s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return root;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.mapita) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    Toast.makeText(getContext(), "Something has been clicked: "+mapa.getSilla(event.getX(), event.getY()).getId(), Toast.LENGTH_LONG).show();
                    goTo(mapa.getSilla(event.getX(), event.getY()).getId());
                    return true;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    return true;
            }
        }
        return false;
    }

    public void goTo(String id) {
        Intent intent = new Intent(getActivity(), Reserva.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
