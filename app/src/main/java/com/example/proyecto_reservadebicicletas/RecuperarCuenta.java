package com.example.proyecto_reservadebicicletas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecuperarCuenta extends AppCompatActivity {
    private TextView textMail;
    private Button btnReset;
    private String email="";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cuenta);

        textMail=(TextView) findViewById(R.id.mailReset);
        btnReset=(Button)findViewById(R.id.botonReset);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=textMail.getText().toString();
                if(!email.isEmpty()){
                    mDialog.setMessage("Espera por favor...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else {
                    Toast.makeText(RecuperarCuenta.this,"Ingrese el email en el campo",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    Toast.makeText(RecuperarCuenta.this,
                            "Se envio un correo para el cambio de contraseña", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    mAuth.signOut();
                    startActivity(new Intent(RecuperarCuenta.this,MainActivity.class ));
                    finish();


                } else {
                    Toast.makeText(RecuperarCuenta.this,
                            "No se pudo completar con el correo existente", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }



            }
        });

    }

}
