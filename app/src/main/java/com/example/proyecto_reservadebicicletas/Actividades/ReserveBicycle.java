package com.example.proyecto_reservadebicicletas.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyecto_reservadebicicletas.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReserveBicycle extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar__bicicleta__electrica);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EditText textView_fecha_inicial = findViewById(R.id.view_inicio);
        EditText textView_fecha_final = findViewById(R.id.view_final);
        Button boton_elegir = findViewById(R.id.btn_elegir_fecha_reserva);
        Button boton_reservar = findViewById(R.id.btn_realizar_reserva);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        textView_fecha_inicial.setEnabled(false);
        textView_fecha_final.setEnabled(false);
        boton_elegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ReserveBicycle.this,Calendario.class);
                startActivity(intent);
            }
        });

        boton_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReserveBicycle.this);
                    builder.setMessage("¿Desea realizar la reverca con esas fechas?");
                    builder.setTitle("Reservar Bicicleta");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    return;
                                }
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String email_obtenido = getFromSharesPreferences();
                                    if (ds.child("e-mail").getValue().toString().equals(email_obtenido)) {
                                        final String ID = ds.getKey();
                                        final HashMap<String, Object> map = new HashMap<>();
                                        map.put("reserva", "1");
                                        databaseReference.child("Users").child(ID).updateChildren(map);
                                        Intent intent = new Intent(ReserveBicycle.this, CancelarReserva.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
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
        });

        Intent intent = getIntent();
        String date = intent.getStringExtra("fecha_inicial");
        String date2 = intent.getStringExtra("fecha_final");
        textView_fecha_inicial.setText(date);
        textView_fecha_final.setText(date2);

    }

    private String getFromSharesPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("login_preferences",Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL", "");

    }
}