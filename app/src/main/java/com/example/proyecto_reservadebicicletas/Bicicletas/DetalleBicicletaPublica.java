package com.example.proyecto_reservadebicicletas.Bicicletas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_reservadebicicletas.Actividades.ReserveBicycle;
import com.example.proyecto_reservadebicicletas.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetalleBicicletaPublica extends AppCompatActivity {
    private TextView view_nombre;
    private TextView view_marca;
    private TextView view_modelo;
    private TextView view_tipo;
    private TextView view_cantidad;
    private TextView view_precio;
    private TextView view_descripcion;
    private ImageView imageView_foto;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bicicleta_publica);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        view_nombre = findViewById(R.id.txtview_nombre);
        view_marca = findViewById(R.id.txtview_marca);
        view_modelo = findViewById(R.id.txtview_modelo);
        view_tipo = findViewById(R.id.txtview_tipo);
        view_cantidad = findViewById(R.id.txtview_cantidad);
        view_precio = findViewById(R.id.txtview_precio);
        view_descripcion = findViewById(R.id.txtview_descripcion);
        imageView_foto = findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        int id = Objects.requireNonNull(extras).getInt("id");
        id = id +1;
        String id1 = String.valueOf(id);

        firebaseFirestore.collection("Bicicletas_Publicas").document(id1).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String Nombre = documentSnapshot.getString("Nombre");
                            String Marca = documentSnapshot.getString("Marca");
                            String Modelo = documentSnapshot.getString("Modelo");
                            String Tipo = documentSnapshot.getString("Tipo");
                            Long Cantidad = documentSnapshot.getLong("Cantidad");
                            Long Precio = documentSnapshot.getLong("Precio");
                            String Descripcion = documentSnapshot.getString("Descripcion");
                            String Image = documentSnapshot.getString("Image");

                            Picasso.get().load(Image).into(imageView_foto);
                            view_nombre.setText(Nombre);
                            view_marca.setText(Marca);
                            view_modelo.setText(Modelo);
                            view_tipo.setText(Tipo);
                            view_cantidad.setText(Objects.requireNonNull(Cantidad).toString());
                            view_precio.setText(Objects.requireNonNull(Precio).toString());
                            view_descripcion.setText(Descripcion);
                        }
                    }
                });
    }
    public void reservarBicicleta(View view){
        Intent intent = new Intent(this, ReserveBicycle.class);
        startActivity(intent);
    }
}