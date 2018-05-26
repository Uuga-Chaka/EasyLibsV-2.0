package icesi.dmi.easylibdos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    //Botonos & campos de texto
    Button btn_login,btn_register;
    EditText et_email,et_password;
    TextView tv_register;

    //Base de datos
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userData = database.getReference("user");

    //Autentificación
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicialización de variables locales
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            updateUI(currentUser);
        }
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register = (TextView) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void updateUI(FirebaseUser currentUser) {
            finish();
            startActivity(new Intent(getApplicationContext(),Map.class));
    }

    @Override
    public void onClick(View v) {
        if(v == btn_login){
            loginUser();
        }

        if(v == tv_register){
           registerUser();
        }
    }

    private void registerUser() {
        //Toast.makeText(MainActivity.this,"Funciona",Toast.LENGTH_LONG).show();
       // finish();
        startActivity(new Intent(this,Register.class));
    }

    private void goToMap() {

    }

    private void loginUser() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(this,"Email is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this,"Password is empty",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("signin","Is working");
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                }
                else{
                    Log.w("signin","Fail");
                }
            }
        });
    }
}
