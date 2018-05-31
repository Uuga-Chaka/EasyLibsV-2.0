package icesi.dmi.easylibdos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;

public class ReservaAhora extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Users").child("Icesi");
    EditText et_inicio, et_final;

    Button btn_reserva;
    boolean hasBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ahora);

        et_inicio = findViewById(R.id.et_inicio);
        et_final = findViewById(R.id.et_fin);


        hasBooking = false;

        btn_reserva = (Button) findViewById(R.id.btn_reserva);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ReservaAhora.this, MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();

        final String uid = user.getUid();
        // Toast.makeText(ReservaAhora.this, String.valueOf(uid), Toast.LENGTH_LONG).show();
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User us = dataSnapshot.getValue(User.class);

                hasBooking = us.hasBooking;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User us = new User();
                if (!hasBooking) {
                    //Puede reservar
                    int in = Integer.parseInt(et_inicio.getText().toString());
                    int fi = Integer.parseInt(et_final.getText().toString());
                    if ((fi - in) < 0) {
                        Toast.makeText(ReservaAhora.this, "La hora de inicio no puede ser mayor a la final", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (fi > 24 || in > 24 || fi < 0 || in < 0) {
                        Toast.makeText(ReservaAhora.this, "Hora no valida", Toast.LENGTH_LONG).show();
                        return;
                    }

                    us.inicio = in;
                    us.fin = fi;
                    us.hasBooking = true;

                    ref.setValue(us);
                } else {
                    //no puede reservar
                    Toast.makeText(ReservaAhora.this, "Actualmente tiene una reserva activa", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
