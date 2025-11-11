package com.example.programaregistrodecontactos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etNombre, etTelefono, etOficina, etCelular, etCorreo;
    private Button btnGuardar, btnVerContactos, btnSalir, btnCancelar;
    private ContactoDAO contactoDAO;
    private int contactoId = -1;
    private boolean modoEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitulo = findViewById(R.id.tvTitulo);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etOficina = findViewById(R.id.etOficina);
        etCelular = findViewById(R.id.etCelular);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerContactos = findViewById(R.id.btnVerContactos);
        btnSalir = findViewById(R.id.btnSalir);
        btnCancelar = findViewById(R.id.btnCancelar);

        contactoDAO = new ContactoDAO(this);
        contactoDAO.abrir();

        verificarModoEdicion();

        btnGuardar.setOnClickListener(v -> guardarContacto());

        btnVerContactos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaContactosActivity.class);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v -> {
            if (modoEdicion) {
                limpiarCampos();
                modoEdicion = false;
                contactoId = -1;
                tvTitulo.setText("Registro de Contactos");
                btnCancelar.setVisibility(android.view.View.GONE);
                btnGuardar.setText("GUARDAR CONTACTO");
            }
        });

        btnSalir.setOnClickListener(v -> finish());
    }

    private void verificarModoEdicion() {
        if (getIntent().hasExtra("contacto_id")) {
            contactoId = getIntent().getIntExtra("contacto_id", -1);
            modoEdicion = true;
            tvTitulo.setText("Editar Contacto");
            btnGuardar.setText("ACTUALIZAR CONTACTO");
            btnCancelar.setVisibility(android.view.View.VISIBLE);
            cargarDatosContacto();
        } else {
            btnCancelar.setVisibility(android.view.View.GONE);
        }
    }

    private void cargarDatosContacto() {
        Contacto contacto = contactoDAO.obtenerContactoPorId(contactoId);
        if (contacto != null) {
            etNombre.setText(contacto.getNombre());
            etTelefono.setText(contacto.getTelefono());
            etOficina.setText(contacto.getOficina());
            etCelular.setText(contacto.getCelular());
            etCorreo.setText(contacto.getCorreo());
        }
    }

    private void guardarContacto() {
        String nombre = etNombre.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String oficina = etOficina.getText().toString().trim();
        String celular = etCelular.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setOficina(oficina);
        contacto.setCelular(celular);
        contacto.setCorreo(correo);

        if (modoEdicion) {
            contacto.setId(contactoId);
            int resultado = contactoDAO.actualizarContacto(contacto);
            if (resultado > 0) {
                Toast.makeText(this, "Contacto actualizado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                modoEdicion = false;
                contactoId = -1;
                tvTitulo.setText("Registro de Contactos");
                btnCancelar.setVisibility(android.view.View.GONE);
                btnGuardar.setText("GUARDAR CONTACTO");
            } else {
                Toast.makeText(this, "Error al actualizar contacto", Toast.LENGTH_SHORT).show();
            }
        } else {
            long resultado = contactoDAO.agregarContacto(contacto);
            if (resultado != -1) {
                Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al guardar contacto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etTelefono.setText("");
        etOficina.setText("");
        etCelular.setText("");
        etCorreo.setText("");
        etNombre.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactoDAO.cerrar();
    }
}