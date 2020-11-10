package com.marcosledesma.agendacontactosedu.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Direccion implements Parcelable {
    private String nombre;
    private String calle;
    private int numero;
    private String ciudad;

    public Direccion() {
    }

    public Direccion(String nombre, String calle, int numero, String ciudad) {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
    }

    protected Direccion(Parcel in) {
        nombre = in.readString();
        calle = in.readString();
        numero = in.readInt();
        ciudad = in.readString();
    }

    public static final Creator<Direccion> CREATOR = new Creator<Direccion>() {
        @Override
        public Direccion createFromParcel(Parcel in) {
            return new Direccion(in);
        }

        @Override
        public Direccion[] newArray(int size) {
            return new Direccion[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(calle);
        dest.writeInt(numero);
        dest.writeString(ciudad);
    }
}
