package icesi.dmi.easylibdos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservaAhora extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Users").child("Icesi");

    Button btn_reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ahora);

        btn_reserva = (Button) findViewById(R.id.btn_reserva);

       // btn_reserva.addOn

        String uid = user.getUid();
        ref.child(uid);
    }
}
