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
import com.marcosledesma.agendacontactosedu.modelos.Direccion;

import java.util.List;

public class DireccionesAdapter extends ArrayAdapter<Direccion> {
    // Definir variables
    private Context context;
    private int resource;
    private List<Direccion> objects;

    public DireccionesAdapter(@NonNull Context context, int resource, @NonNull List<Direccion> objects) {
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
        // Montamos View
        View fila = LayoutInflater.from(context).inflate(resource, null);
        // Asociar variables y vista
        TextView txtTipo = fila.findViewById(R.id.txtTipoDireccionFilaDir);
        TextView txtCalle = fila.findViewById(R.id.txtCalleFilaDir);
        TextView txtNumero = fila.findViewById(R.id.txtNumeroFilaDir);
        TextView txtCiudad = fila.findViewById(R.id.txtCiudadFilaDir);
        // Asignar valores de la lista a los txt
        txtTipo.setText(objects.get(position).getNombre());
        txtCalle.setText(objects.get(position).getCalle());
        txtNumero.setText(String.valueOf(objects.get(position).getNumero()));
        txtCiudad.setText(objects.get(position).getCiudad());
        // Devolver View
        return fila;
    }
}
