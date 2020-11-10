package com.marcosledesma.agendacontactosedu.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Telefono implements Parcelable {
    private String nombre;
    private String telefono;

    public Telefono() {
    }

    public Telefono(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    protected Telefono(Parcel in) {
        nombre = in.readString();
        telefono = in.readString();
    }

    public static final Creator<Telefono> CREATOR = new Creator<Telefono>() {
        @Override
        public Telefono createFromParcel(Parcel in) {
            return new Telefono(in);
        }

        @Override
        public Telefono[] newArray(int size) {
            return new Telefono[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(telefono);
    }
}
