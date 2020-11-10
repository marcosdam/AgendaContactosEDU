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
import com.marcosledesma.agendacontactosedu.modelos.Telefono;

import java.util.List;

public class TelefonosAdapter extends ArrayAdapter<Telefono> {
    // Declarar variables
    private Context context;
    private int resource;
    private List<Telefono> objects;

    public TelefonosAdapter(@NonNull Context context, int resource, @NonNull List<Telefono> objects) {
        super(context, resource, objects);
        // Inicializar variables
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Quitamos el super
        // Creamos la View
        View fila = LayoutInflater.from(context).inflate(resource, null);
        // Asociar variables con la Vista
        TextView txtNombre = fila.findViewById(android.R.id.text1);
        TextView txtNumTel = fila.findViewById(android.R.id.text2);
        // Asignar valores a los txt
        txtNombre.setText(objects.get(position).getNombre());
        txtNumTel.setText(objects.get(position).getTelefono());
        // Devolvemos la View
        return fila;
    }
}
