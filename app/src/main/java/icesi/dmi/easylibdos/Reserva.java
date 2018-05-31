package icesi.dmi.easylibdos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reserva extends AppCompatActivity {


    TextView tv_status,tv_id;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        intent = getIntent();
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_id = (TextView) findViewById(R.id.tv_id);

        final String id = intent.getStringExtra("id");
        ref = db.getReference().child("Bibliotech").child("Universidades").child("Icesi").child(id);
        tv_status.setText(id);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String color;
                Coordenada coordenada = dataSnapshot.getValue(Coordenada.class);

                if (coordenada.state == 0) {
                    color = "Libre";
                }
                else if (coordenada.state == 1) {
                    color = "Ocupado";
                }
                else {
                    color = "Reservado";
                }
                tv_status.setText(color);
                tv_id.setText(coordenada.ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void lookFor(String id) {
        ref.child(id);
    }
}
