package icesi.dmi.easylibdos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_pass, et_repass;
    Button btn_register;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference().child("Bibliotech").child("Users").child("Icesi");
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    FirebaseAuth mAuth;
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = (Button) findViewById(R.id.btn_registe);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_repass = (EditText) findViewById(R.id.et_repass);

        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_register) {

            // filtros de validación de contraseña y usuario
            String email = et_email.getText().toString().trim();
            String password = et_pass.getText().toString().trim();
            String repass = et_repass.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(Register.this, "El correo está vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (!validate(email)) {
                Toast.makeText(Register.this, "El correo es invalido", Toast.LENGTH_LONG).show();

                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(Register.this, "No hay contraseña", Toast.LENGTH_LONG).show();
                return;
            }

            if (repass.isEmpty()) {
                Toast.makeText(Register.this, "Repite la contraseña", Toast.LENGTH_LONG).show();
                return;
            }

            if (!password.equals(repass)) {
                Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(Register.this, "Contraseña menor a 6 caracteres", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(Register.this, email, Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String UID = currentUser.getUid();
                        User us = new User(UID, false);
                        ref.child(UID).setValue(us);
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(Register.this, "Not good", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
