package com.arp.practicafragmentos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alex on 04/12/2015.
 */
public class Contacto implements Serializable,Comparable<Contacto>{

    private long id;
    private File foto;
    private String nombre;
    private ArrayList<String> tlfs;

    public Contacto() {
    }

    public Contacto(long id,File foto, String nombre, ArrayList<String> tlfs) {
        this.id = id;
        this.foto=foto;
        this.nombre = nombre;
        this.tlfs = tlfs;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getTlfs() {
        return tlfs;
    }

    public void setTlfs(ArrayList<String> tlfs) {
        this.tlfs = tlfs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacto contacto = (Contacto) o;

        return id == contacto.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Contacto another) {
        int r=nombre.compareToIgnoreCase(another.nombre);
        if(r==0){
            r=(int)(id-another.id);
        }
        return r;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", foto=" + foto +
                ", nombre='" + nombre + '\'' +
                ", tlfs=" + tlfs +
                '}';
    }
}
