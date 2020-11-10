package com.marcosledesma.agendacontactosedu.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Contacto implements Parcelable {
    private String nombre;
    private String apellidos;
    private String empresa;
    private ArrayList<Telefono> telefonos;
    private ArrayList<Direccion> direcciones;

    public Contacto() {
        telefonos = new ArrayList<>();
        direcciones = new ArrayList<>();
    }

    public Contacto(String nombre, String apellidos, String empresa, ArrayList<Telefono> telefonos, ArrayList<Direccion> direcciones) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.empresa = empresa;
        this.telefonos = telefonos;
        this.direcciones = direcciones;
    }

    protected Contacto(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        empresa = in.readString();
        telefonos = in.createTypedArrayList(Telefono.CREATOR);
        direcciones = in.createTypedArrayList(Direccion.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(empresa);
        dest.writeTypedList(telefonos);
        dest.writeTypedList(direcciones);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

}
