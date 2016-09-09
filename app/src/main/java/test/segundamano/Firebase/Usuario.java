package test.segundamano.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mireia on 9/09/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Usuario {

    // String
    private String nombre = "Undefined";        // nombre del usuario
    private String edad = "Undefined";          // edad del usuario
    private String descripcion = "Undefined";   // Descripción usuario
    private String rutaImagen = "Undefined";    // Ruta en la que se alamcenará la ruta del usuario

    // Constructores

    public Usuario() {}

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

}