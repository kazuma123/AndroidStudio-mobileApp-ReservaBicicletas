package com.example.proyecto_reservadebicicletas.Bicicletas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proyecto_reservadebicicletas.R;

public class ListaDeBicicletasPublicas extends AppCompatActivity {

    private ListViewAdapter adapterPublic;
    private Intent intent;
    private final String[] titulo = new String[]{
            "Ortler Mainau, black matt (2020)",
            "FIXIE Inc. Floater Race Street 8S Disc, silver (2020)",

    };

    private final int[] imagenes = {
            R.drawable.bike_ortler,
            R.drawable.bike_fixie,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_bicicletas_publicas);

        final ListView lista = findViewById(R.id.listViewpublic);
        adapterPublic = new ListViewAdapter(this, titulo, imagenes);
        lista.setAdapter(adapterPublic);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "presiono " + position, Toast.LENGTH_SHORT).show();
                intent = new Intent(ListaDeBicicletasPublicas.this, DetalleBicicletaPublica.class);
                intent.putExtra("id",position);
                startActivity(intent);

            }
        });
    }
}