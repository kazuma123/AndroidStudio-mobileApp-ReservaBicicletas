package com.example.proyecto_reservadebicicletas.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_reservadebicicletas.MainActivity;
import com.example.proyecto_reservadebicicletas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText txt_dni;
    private EditText txt_nombre;
    private EditText txt_email;
    private EditText txt_password;
    private EditText txt_telefono;

    private String dni;
    private String nombre;
    private String email;
    private String password;
    private String telefono;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    //FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        txt_dni = findViewById(R.id.txt_dni);
        txt_nombre = findViewById(R.id.txt_usuario);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_telefono = findViewById(R.id.txt_telefono);


    }

    public void Registrar(View view){
        dni = txt_dni.getText().toString();
        nombre = txt_nombre.getText().toString();
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();
        telefono = txt_telefono.getText().toString();

        if(TextUtils.isEmpty(dni)){
            txt_dni.setError("Su DNI es requerido");
            return;
        }
        if(dni.length() == 7){
            txt_dni.setError("No es valido su DNI");
            return;
        }
        if(TextUtils.isEmpty(nombre)){
            txt_nombre.setError("El usuario es requerido");
            return;
        }
        if(TextUtils.isEmpty(email)){
            txt_email.setError("E-mail es requerido");
            return;
        }
        if(TextUtils.isEmpty(password)){
            txt_password.setError("Contraseña es requerido");
            return;
        }
        if(password.length() < 6){
            txt_password.setError("La contraseña debe ser mayor a 6 caracteres");
            return;
        }
        if(TextUtils.isEmpty(telefono)){
            txt_telefono.setError("El numero de celular es requerido");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("dni",dni);
                    map.put("usuario",nombre);
                    map.put("e-mail",email);
                    map.put("contraseña",password);
                    map.put("telefono",telefono);
                    map.put("reserva","0");

                    String id = firebaseAuth.getCurrentUser().getUid();

                    firebaseDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){

                                Toast.makeText(RegistrarUsuario.this,"Registro completado, Mensaje enviado",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegistrarUsuario.this,"Verificacion de email ha sido enviado",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistrarUsuario.this,"Error al enviar Email",Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }else {
                                Toast.makeText(RegistrarUsuario.this,"No se puedo crear los datos",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(RegistrarUsuario.this,"No se ha podido registrar el usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
