package icesi.dmi.easylibdos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Reserva extends AppCompatActivity {


    TextView tv_status, tv_id;

    EditText et_inicio, et_final;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref, userdb;

    boolean hasBooking;
    Button btn_reserva;

    Intent intent;

    Coordenada c;
    User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        intent = getIntent();
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_id = (TextView) findViewById(R.id.tv_id);

        //EditText input

        et_inicio = (EditText) findViewById(R.id.et_inicio);
        et_final = (EditText) findViewById(R.id.et_fin);

        btn_reserva = (Button) findViewById(R.id.btn_reservar);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Reserva.this, MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();


        final String id = intent.getStringExtra("id");
        ref = db.getReference().child("Bibliotech").child("Universidades").child("Icesi").child(id);
        userdb = db.getReference().child("Bibliotech").child("Users").child("Icesi").child(user.getUid());
        tv_status.setText(id);

        hasBooking = false;

        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                us = dataSnapshot.getValue(User.class);
                Toast.makeText(Reserva.this, String.valueOf(us.hasBooking), Toast.LENGTH_LONG).show();
                hasBooking = us.hasBooking;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String color;
                c = dataSnapshot.getValue(Coordenada.class);

                if (c.state == 0) {
                    tv_status.setTextColor(ContextCompat.getColor(Reserva.this,R.color.verde));
                    color = "Libre";
                } else if (c.state == 1) {
                    tv_status.setTextColor(ContextCompat.getColor(Reserva.this,R.color.rojo));
                    color = "Ocupado";
                } else {
                    tv_status.setTextColor(ContextCompat.getColor(Reserva.this,R.color.amarillo));
                    color = "Reservado";
                }
                tv_status.setText(color);
                tv_id.setText("Cubiculo: " + c.ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int in = Integer.parseInt(et_inicio.getText().toString().trim());
                int fi = Integer.parseInt(et_final.getText().toString().trim());

                Calendar calendar = GregorianCalendar.getInstance();
                Date date = new Date();
                calendar.setTime(date);

                Toast.makeText(Reserva.this, et_inicio.getText().toString().trim() + " " + et_final.getText().toString().trim(), Toast.LENGTH_LONG).show();
                if (!hasBooking) {
                    //Puede reservar

                    if ((fi - in) < 0) {
                        Toast.makeText(Reserva.this, "La hora de inicio no puede ser mayor a la final", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (fi > 24 || in > 24 || fi < 0 || in < 0) {
                        Toast.makeText(Reserva.this, "Hora no valida", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (fi < calendar.get(Calendar.HOUR_OF_DAY)) {
                        Toast.makeText(Reserva.this, "Esta hora ya pasó", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Variables para el usuario
                    us.inicio = in;
                    us.fin = fi;
                    us.hasBooking = true;
                    //Variables para el lugar
                    c.inicio = in;
                    c.fin = fi;
                    c.hasReserva = true;
                    c.state = 2;

                    ref.setValue(c);
                    userdb.setValue(us);
                } else {
                    //no puede reservar
                    Toast.makeText(Reserva.this, "Actualmente tiene una reserva activa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
