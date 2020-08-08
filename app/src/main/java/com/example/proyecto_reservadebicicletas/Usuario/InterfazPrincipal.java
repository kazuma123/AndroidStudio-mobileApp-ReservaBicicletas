package com.example.proyecto_reservadebicicletas.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.proyecto_reservadebicicletas.R;
import com.example.proyecto_reservadebicicletas.Terminales.MapsActivityTerminales;
import com.example.proyecto_reservadebicicletas.Terminales.MenuDeOpcionesGraficas;

public class InterfazPrincipal extends AppCompatActivity {

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
        Intent intent = new Intent(getApplicationContext(), ModificarDatosUsuario.class);
        startActivity(intent);
    }
}
