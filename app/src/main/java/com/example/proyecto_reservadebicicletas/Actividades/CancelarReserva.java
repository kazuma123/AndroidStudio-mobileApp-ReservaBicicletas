package com.example.proyecto_reservadebicicletas.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.proyecto_reservadebicicletas.MenuActivity;
import com.example.proyecto_reservadebicicletas.R;
import com.example.proyecto_reservadebicicletas.Usuario.LoginUsuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CancelarReserva extends AppCompatActivity {
    private Boolean corer = false;
    private Chronometer chronometer;
    private DatabaseReference databaseReference;
    private String id;
    private  String email_obtenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_reserva);
        chronometer = findViewById(R.id.chronometer);
        Button btn_devolver_bicicleta = findViewById(R.id.btn_devolver_bicicleta);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        starCronometro();

        btn_devolver_bicicleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                email_obtenido = getFromSharesPreferences();

                                if (ds.child("e-mail").getValue().toString().equals(email_obtenido)) {
                                    id = ds.getKey();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(CancelarReserva.this);
                                    builder.setMessage("¿Seguro que quiere cancelar la reserva?");
                                    builder.setTitle("Cancelar Reserva");
                                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final HashMap<String, Object> map = new HashMap<>();
                                            map.put("reserva", "0");
                                            databaseReference.child("Users").child(id).child("reserva").updateChildren(map);
                                            startActivity(new Intent(CancelarReserva.this, MenuActivity.class));
                                            finish();
                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private String getFromSharesPreferences() {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL", "");
    }

    private void starCronometro() {
        if (!corer){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            corer = false;
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit") .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CancelarReserva.this, LoginUsuario.class);
                        startActivity(intent);
                        finish();
                } }).setNegativeButton("No", null).show();
    }
}