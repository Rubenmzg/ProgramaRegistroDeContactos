package com.example.programaregistrodecontactos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Contacto> listaContactos;
    private OnContactoListener listener;

    public interface OnContactoListener {
        void onEditarClick(Contacto contacto);
        void onEliminarClick(Contacto contacto);
    }

    public ContactoAdapter(List<Contacto> listaContactos, OnContactoListener listener) {
        this.listaContactos = listaContactos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);

        holder.tvNombre.setText(contacto.getNombre());
        holder.tvTelefono.setText("Telefono: " + (contacto.getTelefono() != null && !contacto.getTelefono().isEmpty() ? contacto.getTelefono() : "No disponible"));
        holder.tvOficina.setText("Oficina: " + (contacto.getOficina() != null && !contacto.getOficina().isEmpty() ? contacto.getOficina() : "No disponible"));
        holder.tvCelular.setText("Celular: " + (contacto.getCelular() != null && !contacto.getCelular().isEmpty() ? contacto.getCelular() : "No disponible"));
        holder.tvCorreo.setText("Correo: " + (contacto.getCorreo() != null && !contacto.getCorreo().isEmpty() ? contacto.getCorreo() : "No disponible"));

        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditarClick(contacto);
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminarClick(contacto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public void actualizarLista(List<Contacto> nuevaLista) {
        this.listaContactos = nuevaLista;
        notifyDataSetChanged();
    }

    static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono, tvOficina, tvCelular, tvCorreo;
        Button btnEditar, btnEliminar;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvOficina = itemView.findViewById(R.id.tvOficina);
            tvCelular = itemView.findViewById(R.id.tvCelular);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
