package com.example.proyecto_reservadebicicletas.Terminales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.proyecto_reservadebicicletas.Bicicletas.ListaDeBicicletasElectricas;
import com.example.proyecto_reservadebicicletas.Bicicletas.ListaDeBicicletasPublicas;
import com.example.proyecto_reservadebicicletas.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MenuDeOpcionesGraficas extends AppCompatActivity {

    private Intent intent;
    private FirebaseFirestore firebaseFirestore;
    private DatabaseReference databaseReference;
    private String codigo;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_de_opciones_graficas);
        CardView bipublicas = findViewById(R.id.card_bipublicas);
        CardView bielectricas = findViewById(R.id.card_bikelectric);
        CardView terminalFavorito = findViewById(R.id.card_terminal_favorito);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        bielectricas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListaDeBicicletasElectricas.class);
                startActivity(intent);
            }
        });

        bipublicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListaDeBicicletasPublicas.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        codigo = extras.getString("codigo");

        terminalFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Terminales").document(codigo).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    final String Nombre = documentSnapshot.getString("Nombre");
                                    final String Descripcion = documentSnapshot.getString("Descripcion");

                                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()){
                                                for (DataSnapshot ds: dataSnapshot.getChildren()){
                                                    String email_obtenido = getFromSharesPreferences();

                                                    if(ds.child("e-mail").getValue().toString().equals(email_obtenido)){
                                                        id = ds.getKey();

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuDeOpcionesGraficas.this);
                                                        builder.setMessage("¿Seguro que quiere cancelar la reserva?");
                                                        builder.setTitle("Cancelar Reserva");
                                                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                final HashMap<String, Object> map = new HashMap<>();
                                                                map.put("Nombre",Nombre);
                                                                map.put("Descripcion",Descripcion);
                                                                databaseReference.child("Users").child(id).child("terminal_favorito").updateChildren(map);
                                                                Toast.makeText(MenuDeOpcionesGraficas.this,"Terminal agregado",Toast.LENGTH_SHORT).show();
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
                            }
                        });
            }
        });


    }
    private String getFromSharesPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL", "");
    }
}