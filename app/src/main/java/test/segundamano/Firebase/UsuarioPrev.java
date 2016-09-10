package test.segundamano.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mireia on 10/09/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class UsuarioPrev {

    // String
    private String nombre = "Undefined";        // nombre del usuario
    private String edad = "Undefined";          // edad del usuario
    private String resumen = "Undefined";       // Descripción usuario
    private String rutaImagen = "Undefined";    // Ruta en la que se alamcenará la ruta del usuario

    // Constructores

    public UsuarioPrev() {}

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getResumen() {
        return resumen;
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

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

}