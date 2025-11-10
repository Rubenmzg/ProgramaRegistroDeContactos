package com.example.programaregistrodecontactos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListaContactosActivity extends AppCompatActivity implements ContactoAdapter.OnContactoListener {

    private RecyclerView recyclerContactos;
    private TextView tvVacio;
    private Button btnVolver;
    private ContactoAdapter adapter;
    private ContactoDAO contactoDAO;
    private List<Contacto> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        recyclerContactos = findViewById(R.id.recyclerContactos);
        tvVacio = findViewById(R.id.tvVacio);
        btnVolver = findViewById(R.id.btnVolver);

        contactoDAO = new ContactoDAO(this);
        contactoDAO.abrir();

        recyclerContactos.setLayoutManager(new LinearLayoutManager(this));

        cargarContactos();

        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarContactos() {
        listaContactos = contactoDAO.obtenerTodosContactos();

        if (listaContactos.isEmpty()) {
            recyclerContactos.setVisibility(View.GONE);
            tvVacio.setVisibility(View.VISIBLE);
        } else {
            recyclerContactos.setVisibility(View.VISIBLE);
            tvVacio.setVisibility(View.GONE);

            if (adapter == null) {
                adapter = new ContactoAdapter(listaContactos, this);
                recyclerContactos.setAdapter(adapter);
            } else {
                adapter.actualizarLista(listaContactos);
            }
        }
    }

    @Override
    public void onEditarClick(Contacto contacto) {
        mostrarDialogoEditar(contacto);
    }

    private void mostrarDialogoEditar(Contacto contacto) {
        new AlertDialog.Builder(this)
                .setTitle("Informacion del Contacto")
                .setMessage(
                        "Nombre: " + contacto.getNombre() + "\n" +
                                "Telefono: " + (contacto.getTelefono() != null ? contacto.getTelefono() : "No disponible") + "\n" +
                                "Oficina: " + (contacto.getOficina() != null ? contacto.getOficina() : "No disponible") + "\n" +
                                "Celular: " + (contacto.getCelular() != null ? contacto.getCelular() : "No disponible") + "\n" +
                                "Correo: " + (contacto.getCorreo() != null ? contacto.getCorreo() : "No disponible")
                )
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onEliminarClick(Contacto contacto) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Contacto")
                .setMessage("Estas seguro de eliminar a " + contacto.getNombre() + "?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    contactoDAO.eliminarContacto(contacto.getId());
                    cargarContactos();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactoDAO.cerrar();
    }
}
