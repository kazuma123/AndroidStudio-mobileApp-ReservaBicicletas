package com.example.proyecto_reservadebicicletas.Usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_reservadebicicletas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ModificarFragment extends Fragment {
    //TODO: Rename and change types of parameters
    private DatabaseReference firebaseDatabase;
    private String email;
    private EditText view_dni;
    private EditText view_user;
    private TextView view_email;
    private EditText view_password;
    private EditText view_phone;
    private String email_obtenido;
    private FirebaseAuth firebaseAuth;
    private String id;
    private String dni;
    private String user;
    private String password;
    private String phone;
    public ModificarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_modificar, container, false);
        view_dni = view.findViewById(R.id.view_dni);
        view_user = view.findViewById(R.id.view_usuario);
        view_email = view.findViewById(R.id.view_email);
        view_password = view.findViewById(R.id.view_password);
        view_phone = view.findViewById(R.id.view_telefono);
        firebaseAuth = FirebaseAuth.getInstance();
        Button actualize = view.findViewById(R.id.btn_actualizar);
        Button cargarDatos = view.findViewById(R.id.btn_cargarDatos);
        habilitar();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        cargarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    firebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                email_obtenido = getFromSharesPreferences();
                                for (DataSnapshot ds: dataSnapshot.getChildren()){
                                    if(ds.child("e-mail").getValue().toString().equals(email_obtenido)){
                                        view_dni.setEnabled(false);
                                        view_user.setEnabled(true);
                                        view_password.setEnabled(true);
                                        view_phone.setEnabled(true);
                                        cargarDatos();
                                    }

                                }
                            }
                            else {
                                Toast.makeText(getContext(),"No existe el DNI",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
        });

        actualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("¿Quieres modificar tu cuenta?");
                builder.setTitle("Eliminar Cuenta");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final HashMap<String, Object> map = new HashMap<>();
                        String nuevo_phone = view_phone.getText().toString();
                        String nuevo_user = view_user.getText().toString();
                        String nuevo_password = view_password.getText().toString();
                        map.put("dni",dni);
                        map.put("usuario",nuevo_user);
                        map.put("contraseña",nuevo_password);
                        map.put("e-mail",email);
                        map.put("telefono",nuevo_phone);
                        map.put("reserva","0");

                        firebaseDatabase.child("Users").child(id).setValue(map);
                        Toast.makeText(getContext(),"Se ha modificado su cuenta", Toast.LENGTH_LONG).show();
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

        return view;
    }

    private void cargarDatos() {

        firebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        if(ds.child("e-mail").getValue().toString().equals(email_obtenido)){
                            id = ds.getKey();
                            dni = ds.child("dni").getValue().toString();
                            user = ds.child("usuario").getValue().toString();
                            email = ds.child("e-mail").getValue().toString();
                            password = ds.child("contraseña").getValue().toString();
                            phone = ds.child("telefono").getValue().toString();
                        }
                    }
                    view_dni.setText(dni);
                    view_user.setText(user);
                    view_email.setText(email);
                    view_password.setText(password);
                    view_phone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void habilitar() {
        view_dni.setEnabled(false);
        view_user.setEnabled(false);
        view_password.setEnabled(false);
        view_phone.setEnabled(false);
    }
    private String getFromSharesPreferences() {
        SharedPreferences sharedPref;
        sharedPref = getActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL", "");
    }

}