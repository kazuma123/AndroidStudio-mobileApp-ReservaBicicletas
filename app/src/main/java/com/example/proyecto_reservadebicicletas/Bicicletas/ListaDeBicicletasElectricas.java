package com.example.proyecto_reservadebicicletas.Bicicletas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proyecto_reservadebicicletas.R;


public class ListaDeBicicletasElectricas extends Activity {
    private ListViewAdapter adapter;
    private Intent intent;
    private final String[] titulo = new String[]{
            "Cube Compact Hybrid 20, iridium'n'black (2020)",
            "Electra Townie Path Go! 10D 27,5 Hombre, nardo grey (2020)",
            "ORBEA Gain F20, black (2020)",
            "S'cool e-troX 20 7-S Niños, darkgrey matt (2020)",
            "Serious Leeds 29, silver/black (2020)",
            "Vermont Caravan 20, black",
            "Winora Sinus iR8 Hombre, onyx black (2020)",
    };

    private final int[] imagenes = {
            R.drawable.bike_cube,
            R.drawable.bike_electra,
            R.drawable.bike_orbea,
            R.drawable.bike_scool,
            R.drawable.bike_serious,
            R.drawable.bike_vermont,
            R.drawable.bike_winora
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_bicicletas_electricas);

        final ListView lista = findViewById(R.id.listView1);
        adapter = new ListViewAdapter(this, titulo, imagenes);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "presiono " + position, Toast.LENGTH_SHORT).show();
                intent = new Intent(ListaDeBicicletasElectricas.this, DetalleBicicletaElectrica.class);
                intent.putExtra("id",position);
                startActivity(intent);

            }
        });
    }
}