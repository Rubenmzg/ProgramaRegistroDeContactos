package com.example.programaregistrodecontactos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListaContactosActivity extends AppCompatActivity {

    private LinearLayout layoutContactos;
    private Button btnVolver;
    private ContactoDAO contactoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        layoutContactos = findViewById(R.id.layoutContactos);
        btnVolver = findViewById(R.id.btnVolver);

        contactoDAO = new ContactoDAO(this);
        contactoDAO.abrir();

        cargarContactos();

        btnVolver.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarContactos(); // por si regresamos de editar
    }

    private void cargarContactos() {
        List<Contacto> contactos = contactoDAO.obtenerTodosContactos();
        layoutContactos.removeAllViews();

        // Recorremos de 2 en 2
        for (int i = 0; i < contactos.size(); i += 2) {

            // Fila horizontal
            LinearLayout fila = new LinearLayout(this);
            fila.setOrientation(LinearLayout.HORIZONTAL);
            fila.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // ---- PRIMER CONTACTO (posición i) ----
            Contacto c1 = contactos.get(i);
            View item1 = crearVistaContacto(c1);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            fila.addView(item1, params1);

            // ---- SEGUNDO CONTACTO (posición i+1), si existe ----
            if (i + 1 < contactos.size()) {
                Contacto c2 = contactos.get(i + 1);
                View item2 = crearVistaContacto(c2);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );
                fila.addView(item2, params2);
            } else {
                // Si hay número impar, agregamos un espacio vacío para que se vea centrado
                View espacioVacio = new View(this);
                LinearLayout.LayoutParams paramsVacio = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );
                fila.addView(espacioVacio, paramsVacio);
            }

            // Agregamos la fila completa al layout padre
            layoutContactos.addView(fila);
        }
    }

    /**
     * Crea la vista de un contacto (el item cuadradito) y le configura texto y botones.
     */
    private View crearVistaContacto(Contacto contacto) {
        View item = getLayoutInflater().inflate(R.layout.item_contacto, null, false);

        TextView tvNombre   = item.findViewById(R.id.tvNombre);
        TextView tvTelefono = item.findViewById(R.id.tvTelefono);
        TextView tvCorreo   = item.findViewById(R.id.tvCorreo);
        Button btnEditar    = item.findViewById(R.id.btnEditar);
        Button btnEliminar  = item.findViewById(R.id.btnEliminar);

        tvNombre.setText("Nombre: " + contacto.getNombre());
        tvTelefono.setText("Teléfono: " + vacioComoNoDisponible(contacto.getTelefono()));
        tvCorreo.setText("Correo: " + vacioComoNoDisponible(contacto.getCorreo()));

        // EDITAR
        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(ListaContactosActivity.this, MainActivity.class);
            intent.putExtra("contacto_id", contacto.getId());
            startActivity(intent);
        });

        // ELIMINAR (sin diálogo, como tú querías)
        btnEliminar.setOnClickListener(v -> {
            contactoDAO.eliminarContacto(contacto.getId());
            cargarContactos();
        });

        return item;
    }

    private String vacioComoNoDisponible(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? "No disponible" : valor;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contactoDAO != null) {
            contactoDAO.cerrar();
        }
    }
}

