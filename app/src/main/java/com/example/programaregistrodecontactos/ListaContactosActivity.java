package com.example.programaregistrodecontactos;

import android.os.Bundle;
import android.content.Intent;
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("contacto_id", contacto.getId());
        startActivity(intent);
    }

    @Override
    public void onEliminarClick(Contacto contacto) {

        contactoDAO.eliminarContacto(contacto.getId());
        cargarContactos();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactoDAO.cerrar();
    }
}