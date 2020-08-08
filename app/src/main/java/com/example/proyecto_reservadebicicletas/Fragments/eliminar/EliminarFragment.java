package com.example.proyecto_reservadebicicletas.Fragments.eliminar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.proyecto_reservadebicicletas.MainActivity;
import com.example.proyecto_reservadebicicletas.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EliminarFragment extends Fragment {
    private String email_obtenido;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eliminar, container, false);
        Button eliminar = view.findViewById(R.id.btn_eliminar);
        EditText view_dni = view.findViewById(R.id.view_dni_eliminar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        email_obtenido = getFromSharesPreferences();
        view_dni.setText(email_obtenido);
        view_dni.setEnabled(false);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                for (DataSnapshot ds: dataSnapshot.getChildren()){

                                    if(Objects.requireNonNull(ds.child("e-mail").getValue()).toString().equals(email_obtenido)){
                                        final String ID = ds.getKey();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("¿Quieres eliminar tu cuenta?");
                                        builder.setTitle("Eliminar Cuenta");
                                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                databaseReference.child("Users").child(Objects.requireNonNull(ID)).removeValue();
                                                startActivity(new Intent(getContext(), MainActivity.class));
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


                                    }else {
                                        Toast.makeText(getContext(),"El DNI no existe",Toast.LENGTH_SHORT).show();
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


        return view;
    }
    private String getFromSharesPreferences() {
        SharedPreferences sharedPref;
        sharedPref = requireActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL", "");
    }
}