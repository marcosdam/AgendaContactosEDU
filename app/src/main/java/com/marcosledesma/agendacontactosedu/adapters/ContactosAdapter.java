package com.marcosledesma.agendacontactosedu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.marcosledesma.agendacontactosedu.R;
import com.marcosledesma.agendacontactosedu.modelos.Contacto;

import java.util.List;

public class ContactosAdapter extends ArrayAdapter<Contacto> {
    // Declarar variables
    private Context context;
    private List<Contacto> objects;
    private int resource;

    public ContactosAdapter(@NonNull Context context, int resource, @NonNull List<Contacto> objects) {
        super(context, resource, objects);
        // Inicializar variables
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Eliminamos return por defecto
        // Montamos View
        View fila = LayoutInflater.from(context).inflate(resource, null);
        // Asociar a la vista
        TextView txtNombre = fila.findViewById(R.id.txtNombreContactoFilaContacto);
        TextView txtApellidos = fila.findViewById(R.id.txtApellidosContactoFilaContacto);
        // Accedemos al array de contactos para dar valor a los txt
        txtNombre.setText(objects.get(position).getNombre());
        txtApellidos.setText(objects.get(position).getApellidos());
        // Devolvemos el View
        return fila;
    }
}
