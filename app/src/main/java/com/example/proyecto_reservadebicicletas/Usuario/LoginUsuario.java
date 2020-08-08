package com.example.proyecto_reservadebicicletas.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_reservadebicicletas.Actividades.CancelarReserva;

import com.example.proyecto_reservadebicicletas.MenuActivity;
import com.example.proyecto_reservadebicicletas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUsuario extends AppCompatActivity {
    private EditText txt_email;
    private EditText txt_password;
    private String email;
    private String password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        txt_email = findViewById(R.id.edtUsuario);
        txt_password = findViewById(R.id.edtPassword);
        Button button = findViewById(R.id.btnLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = txt_email.getText().toString();
                password = txt_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    progressDialog = new ProgressDialog(LoginUsuario.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    Login();

                }
                if(email.isEmpty() && password.isEmpty()){
                    txt_email.setError("E-mail es requerido");
                    txt_password.setError("Contraseña es requerido");

                }
                if(TextUtils.isEmpty(email)){
                    txt_email.setError("E-mail es requerido");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    txt_password.setError("Contraseña es requerido");
                }
            }
        });

    }
    private void Login() {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        progressDialog.dismiss();
                        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.child("e-mail").getValue().toString().equals(email)) {
                                            Toast.makeText(LoginUsuario.this, "Logueo Exitoso", Toast.LENGTH_SHORT).show();
                                            String reserva = ds.child("reserva").getValue().toString();
                                            if (reserva.equals("1")) {
                                                GuardarUsuario(email);
                                                Intent intent = new Intent(LoginUsuario.this, CancelarReserva.class);
                                                startActivity(intent);
                                            } else {
                                                GuardarUsuario(email);
                                                Intent intent = new Intent(LoginUsuario.this, MenuActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(LoginUsuario.this,"No ha verificado su cuenta Email",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }else{
                    Toast.makeText(LoginUsuario.this,"Usted no esta registrado",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void GuardarUsuario(String email) {
        SharedPreferences sharedPref = getSharedPreferences("login_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", email);
        editor.apply();
    }

    public void RegistrarUsuario(View view){
        Intent intent = new Intent(LoginUsuario.this, RegistrarUsuario.class);
        startActivity(intent);
    }

}