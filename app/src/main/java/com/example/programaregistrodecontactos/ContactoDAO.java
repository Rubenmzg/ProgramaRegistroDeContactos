package com.example.programaregistrodecontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {

    private ContactoDBHelper dbHelper;
    private SQLiteDatabase database;

    public ContactoDAO(Context context) {
        dbHelper = new ContactoDBHelper(context);
    }

    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        dbHelper.close();
    }

    public long agregarContacto(Contacto contacto) {
        ContentValues valores = new ContentValues();
        valores.put(ContactoDBHelper.COLUMN_NOMBRE, contacto.getNombre());
        valores.put(ContactoDBHelper.COLUMN_TELEFONO, contacto.getTelefono());
        valores.put(ContactoDBHelper.COLUMN_OFICINA, contacto.getOficina());
        valores.put(ContactoDBHelper.COLUMN_CELULAR, contacto.getCelular());
        valores.put(ContactoDBHelper.COLUMN_CORREO, contacto.getCorreo());

        return database.insert(ContactoDBHelper.TABLE_CONTACTOS, null, valores);
    }

    public int actualizarContacto(Contacto contacto) {
        ContentValues valores = new ContentValues();
        valores.put(ContactoDBHelper.COLUMN_NOMBRE, contacto.getNombre());
        valores.put(ContactoDBHelper.COLUMN_TELEFONO, contacto.getTelefono());
        valores.put(ContactoDBHelper.COLUMN_OFICINA, contacto.getOficina());
        valores.put(ContactoDBHelper.COLUMN_CELULAR, contacto.getCelular());
        valores.put(ContactoDBHelper.COLUMN_CORREO, contacto.getCorreo());

        return database.update(ContactoDBHelper.TABLE_CONTACTOS, valores,
                ContactoDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contacto.getId())});
    }

    public int eliminarContacto(int id) {
        return database.delete(ContactoDBHelper.TABLE_CONTACTOS,
                ContactoDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public List<Contacto> obtenerTodosContactos() {
        List<Contacto> contactos = new ArrayList<>();

        Cursor cursor = database.query(ContactoDBHelper.TABLE_CONTACTOS,
                null, null, null, null, null,
                ContactoDBHelper.COLUMN_NOMBRE + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Contacto contacto = new Contacto();
                contacto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_ID)));
                contacto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_NOMBRE)));
                contacto.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_TELEFONO)));
                contacto.setOficina(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_OFICINA)));
                contacto.setCelular(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_CELULAR)));
                contacto.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_CORREO)));
                contactos.add(contacto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactos;
    }

    public Contacto obtenerContactoPorId(int id) {
        Cursor cursor = database.query(ContactoDBHelper.TABLE_CONTACTOS,
                null,
                ContactoDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Contacto contacto = new Contacto();
            contacto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_ID)));
            contacto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_NOMBRE)));
            contacto.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_TELEFONO)));
            contacto.setOficina(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_OFICINA)));
            contacto.setCelular(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_CELULAR)));
            contacto.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(ContactoDBHelper.COLUMN_CORREO)));
            cursor.close();
            return contacto;
        }
        return null;
    }
}
