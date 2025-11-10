package com.example.programaregistrodecontactos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etTelefono, etOficina, etCelular, etCorreo;
    private Button btnGuardar, btnVerContactos, btnSalir;
    private ContactoDAO contactoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etOficina = findViewById(R.id.etOficina);
        etCelular = findViewById(R.id.etCelular);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerContactos = findViewById(R.id.btnVerContactos);
        btnSalir = findViewById(R.id.btnSalir);

        contactoDAO = new ContactoDAO(this);
        contactoDAO.abrir();

        btnGuardar.setOnClickListener(v -> guardarContacto());

        btnVerContactos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaContactosActivity.class);
            startActivity(intent);
        });

        btnSalir.setOnClickListener(v -> finish());
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

        long resultado = contactoDAO.agregarContacto(contacto);

        if (resultado != -1) {
            Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar contacto", Toast.LENGTH_SHORT).show();
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