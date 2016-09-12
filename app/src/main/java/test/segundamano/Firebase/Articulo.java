package test.segundamano.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sergi on 12/09/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Articulo {

    // Variables
    String nombre = "Undefined";
    String precio = "Undefined";
    String foto1 = "Undefined";
    String foto2  = "Undefined";
    String descripcion  = "Undefined";

    // Constructores

    public Articulo() {}

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getFoto1() {
        return foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public String getDescripcion() {
        return descripcion;
    }


    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
