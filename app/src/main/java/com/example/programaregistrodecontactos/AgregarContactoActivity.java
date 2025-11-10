package com.example.programaregistrodecontactos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarContactoActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etNombre, etTelefono, etOficina, etCelular, etCorreo;
    private Button btnGuardar, btnCancelar;
    private ContactoDAO contactoDAO;
    private int contactoId = -1;
    private boolean modoEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        tvTitulo = findViewById(R.id.tvTitulo);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etOficina = findViewById(R.id.etOficina);
        etCelular = findViewById(R.id.etCelular);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        contactoDAO = new ContactoDAO(this);
        contactoDAO.abrir();

        if (getIntent().hasExtra("contacto_id")) {
            contactoId = getIntent().getIntExtra("contacto_id", -1);
            modoEdicion = true;
            tvTitulo.setText("Editar Contacto");
            cargarDatosContacto();
        }

        btnGuardar.setOnClickListener(v -> guardarContacto());
        btnCancelar.setOnClickListener(v -> finish());
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
            contactoDAO.actualizarContacto(contacto);
            Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
        } else {
            contactoDAO.agregarContacto(contacto);
            Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactoDAO.cerrar();
    }
}
