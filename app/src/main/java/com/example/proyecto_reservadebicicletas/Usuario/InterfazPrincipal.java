package com.example.proyecto_reservadebicicletas.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.proyecto_reservadebicicletas.Actividades.CancelarReserva;
import com.example.proyecto_reservadebicicletas.R;
import com.example.proyecto_reservadebicicletas.Terminales.MapsActivityTerminales;
import com.example.proyecto_reservadebicicletas.Terminales.MenuDeOpcionesGraficas;
import com.google.firebase.auth.FirebaseAuth;

public class InterfazPrincipal extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_principal);
    }
    public void MapaTerminales(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivityTerminales.class);
        startActivity(intent);
    }
    public void Terminales_Favoritos(View view){
        Intent intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
        startActivity(intent);
    }
    public void Salir(View view){
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            firebaseAuth.signOut();
            startActivity(new Intent(InterfazPrincipal.this,LoginUsuario.class ));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit") .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(InterfazPrincipal.this, LoginUsuario.class);
                        startActivity(intent);
                        finish();
                    } }).setNegativeButton("No", null).show();
    }
}
