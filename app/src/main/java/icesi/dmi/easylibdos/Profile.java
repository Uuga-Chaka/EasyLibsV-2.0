package icesi.dmi.easylibdos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private TextView tv_userName, tv_email;
    private Button btn_logout,btn_reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();


        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_userName = (TextView) findViewById(R.id.tv_username);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_reserva = (Button) findViewById(R.id.btn_reserva);

        btn_logout.setOnClickListener(this);

        tv_email.setText(user.getEmail());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout)
            firebaseAuth.signOut();
        if(v == btn_reserva){
            startActivity(new Intent(Profile.this,ReservaAhora.class));
        }
    }
}
