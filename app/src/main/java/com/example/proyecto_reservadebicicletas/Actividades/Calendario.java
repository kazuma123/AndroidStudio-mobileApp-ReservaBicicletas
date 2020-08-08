package com.example.proyecto_reservadebicicletas.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.proyecto_reservadebicicletas.R;

public class Calendario extends AppCompatActivity {
    private Intent intent;
    private String date;
    private String date2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button button = findViewById(R.id.btn_reservar_bicicleta);

        CalendarView calendarView = findViewById(R.id.calendarInicial);
        CalendarView calendarView2 = findViewById(R.id.calendarFinal);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"/"+month+"/"+(year+1);

            }
        });

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date2 = dayOfMonth+"/"+month+"/"+(year+1);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Calendario.this, ReserveBicycle.class);
                intent.putExtra("fecha_inicial",date);
                intent.putExtra("fecha_final",date2);
                startActivity(intent);
            }
        });
    }
}