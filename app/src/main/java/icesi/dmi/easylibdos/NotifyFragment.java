package icesi.dmi.easylibdos;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {

    CountDownTimer countDownTimer;

    int prueba = 0;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference lib, userdb;

    boolean hasBooking;
    boolean reservaStarted;

    String lugar;

    TextView tv_text;

    //Get hora de reserva from firebase;

    int horaInicio;
    int horaTerminacion;
    int horaFaltante;
    int minReserva;

    Thread t;

    Coordenada c;
    User us;

    Button btn_terminar;


//    int timeToFinal = currentTime-time needed;

    TextView tv_timer;
    Handler hd;

    public NotifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notify, container, false);
        tv_timer = root.findViewById(R.id.tv_timer);
        tv_text = root.findViewById(R.id.tv_text);

        btn_terminar = root.findViewById(R.id.btn_terminar);

        //iniializar la base de datos y autentificacion

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //hasBooking = false;
        reservaStarted = false;
        horaFaltante = 0;
        minReserva = 0;

        //handler
        hd = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //Una machetada de timer
                if (hasBooking) {

                    Calendar calendar = GregorianCalendar.getInstance();
                    Date date = new Date();
                    calendar.setTime(date);
                    float currentTime = (Calendar.HOUR_OF_DAY + (calendar.get(Calendar.MINUTE) / 60));

                    if (!reservaStarted && currentTime > horaInicio && currentTime < horaTerminacion)
                        reservaStarted = true;
                    else if (reservaStarted && currentTime > horaTerminacion) {
                        c.hasReserva = false;
                        c.state = 0;
                        c.inicio = 0;
                        c.fin = 0;
                        us.fin = 0;
                        us.inicio = 0;
                        us.hasBooking = false;
                        lib.setValue(c);
                        userdb.setValue(us);
                    }


                    if (!reservaStarted) {
                        horaFaltante = horaInicio - calendar.get(Calendar.HOUR_OF_DAY);
                        minReserva = 60 - calendar.get(Calendar.MINUTE);
                        reservaStarted = true;
                        tv_text.setText("Tu reserva empieza en:");
                    } else {
                        horaFaltante = horaTerminacion - calendar.get(Calendar.HOUR_OF_DAY);
                        minReserva = 60 - calendar.get(Calendar.MINUTE);
                        tv_text.setText("Tu reserva termina en:");
                    }

                    String m;
                    String h = String.valueOf(horaFaltante);
                    if (minReserva < 10) {
                        m = String.valueOf("0" + minReserva);
                    } else {
                        m = String.valueOf(minReserva);
                    }
                    String s = String.valueOf(calendar.get(Calendar.SECOND));

                    tv_timer.setText(h + ":" + m + ":" + s);

                    hd.postDelayed(this, 1000);
                }
            }
        };

        hd.postDelayed(runnable, 1000);


        userdb = db.getReference().child("Bibliotech").child("Users").child("Icesi").child(user.getUid());

        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                us = dataSnapshot.getValue(User.class);
                horaInicio = us.inicio;
                horaTerminacion = us.fin;
                hasBooking = us.hasBooking;
                lugar = us.lugar;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(getContext(), String.valueOf(hasBooking), Toast.LENGTH_LONG).show();

        if (hasBooking) {
            lib = db.getReference().child("Bibliotech").child("Universidades").child("Icesi").child(lugar);
            lib.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    c = dataSnapshot.getValue(Coordenada.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            btn_terminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.hasReserva = false;
                    c.state = 0;
                    c.inicio = 0;
                    c.fin = 0;
                    us.fin = 0;
                    us.inicio = 0;
                    us.hasBooking = false;
                    lib.setValue(c);
                    userdb.setValue(us);
                    Toast.makeText(getActivity(), "Funca el click", Toast.LENGTH_LONG).show();
                }
            });
        }

        return root;
    }

}
