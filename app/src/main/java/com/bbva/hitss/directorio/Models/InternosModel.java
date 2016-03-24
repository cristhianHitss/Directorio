package com.bbva.hitss.directorio.Models;

/**
 * Created by Hitss on 23/03/2016.
 */
public class InternosModel {

    private int id;
    private String m;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private int extension;
    private long movil;
    private String correo;
    private String ubicacion;
    private String edificio;
    private String puesto;
    private String rol;
    private String subdireccion;
    private String direccion;
    private int supervisor_inmediato;

    public InternosModel() {
    }

    public InternosModel(int id, String m, String nombre, String apellido_paterno, String apellido_materno, int extension, long movil, String correo, String ubicacion, String edificio, String puesto, String rol, String subdireccion, String direccion, int supervisor_inmediato) {
        this.id = id;
        this.m = m;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.extension = extension;
        this.movil = movil;
        this.correo = correo;
        this.ubicacion = ubicacion;
        this.edificio = edificio;
        this.puesto = puesto;
        this.rol = rol;
        this.subdireccion = subdireccion;
        this.direccion = direccion;
        this.supervisor_inmediato = supervisor_inmediato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    public long getMovil() {
        return movil;
    }

    public void setMovil(long movil) {
        this.movil = movil;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSubdireccion() {
        return subdireccion;
    }

    public void setSubdireccion(String subdireccion) {
        this.subdireccion = subdireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getSupervisor_inmediato() {
        return supervisor_inmediato;
    }

    public void setSupervisor_inmediato(int supervisor_inmediato) {
        this.supervisor_inmediato = supervisor_inmediato;
    }
}
