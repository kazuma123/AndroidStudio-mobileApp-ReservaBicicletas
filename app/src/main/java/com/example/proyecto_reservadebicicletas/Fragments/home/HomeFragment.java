package com.example.proyecto_reservadebicicletas.Fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.proyecto_reservadebicicletas.R;
import com.example.proyecto_reservadebicicletas.Terminales.MapsActivityTerminales;
import com.example.proyecto_reservadebicicletas.Usuario.LoginUsuario;

import java.util.Objects;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnTerminales = view.findViewById(R.id.btnTerminales);

        Button button_salir = view.findViewById(R.id.btn_salir);

        btnTerminales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivityTerminales.class);
                startActivity(intent);
            }
        });
        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginUsuario.class);
                GuardarUsuario();
                startActivity(intent);
            }
        });

        return view;
    }
    private void GuardarUsuario() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", null);
        editor.apply();
    }
}