package icesi.dmi.easylibdos;


import android.content.Intent;
import android.content.res.Resources;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements View.OnTouchListener {

    int[] pos;
    private Mapa mapa;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Universidades").child("Icesi");
    Coordenada refCoordenada;

    //posiciones random para poder poblar el mapa
    //int x = Resources.getSystem().getDisplayMetrics().widthPixels;
    //int y = Resources.getSystem().getDisplayMetrics().heightPixels;


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

        //poblar mapa con posiciones random
        /*for(int i = 0; i<20;i++){
            float xx = (float)Math.random()* x;
            float yy = (float)Math.random()* y;

            Coordenada c = new Coordenada((int)xx,(int)yy,0, String.valueOf(i));
            ref.child(String.valueOf(i)).setValue(c);
        }*/


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapa.clearSilla();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    refCoordenada = child.getValue(Coordenada.class);
                    int x = refCoordenada.x;
                    int y = refCoordenada.y;
                    String id = refCoordenada.ids;
                    int s = refCoordenada.state;
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
                    if (mapa.validate(event.getX(), event.getY())) {
                        String id = mapa.getSilla().getId().trim();
                        if (id.equals(ref.child(id).getKey())) {
                            Toast.makeText(getContext(), "Working on: " + id, Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(getActivity(), Reserva.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    return true;
            }
        }
        return false;
    }
}
