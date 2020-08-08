package com.example.proyecto_reservadebicicletas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyecto_reservadebicicletas.Usuario.LoginUsuario;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ir();
    }

    private void ir() {
        Intent intent = new Intent(MainActivity.this, LoginUsuario.class);
        startActivity(intent);

    }

}
