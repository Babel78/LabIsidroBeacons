package org.giot.applabisidrobeacon.Model;

/**
 * Created by axel on 8/11/17.
 */

public class Calificacion {

    private String nombre;
    private String mensaje;
    private float rating;

    public Calificacion(String nombre, String mensaje, float rating) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.rating = rating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
