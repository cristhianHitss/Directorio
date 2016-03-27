package com.bbva.hitss.directorio.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bbva.R;

/**
 * Created by Hitss on 10/03/2016.
 */
public  class ColaboradorModel implements Parcelable {
    private int id;
    private int no_empleado;
    private String nombre;
    private String apellido_paterno;
    private String apelido_materno;
    private String usuarioxm;
    private String correobbva;
    private String perfil;
    private String ubicacion;
    private String edificio;
    private String extension_bbva;
    private String status;
    private String correo_personal;
    private long celular_personal;
    private String area_laboral;
    private String proyecto_asignado;
    private String expertise;
    private int primer_nivel;
    private int segundo_nivel;
    private int tercer_nivel;

    public ColaboradorModel(int id, int no_empleado, String name, String apellido_paterno, String apelido_materno, String usuarioxm, String correobbva, String edificio, String ubicacion, String area_laboral, String perfil, long celular_personal, String correo_personal, String proyecto_asignado, String expertise, int primer_nivel, int segundo_nivel, int tercer_nivel) {
        this.setId(id);
        this.setNo_empleado(no_empleado);
        this.setPerfil(perfil);
        this.setApellido_paterno(apellido_paterno);
        this.setApelido_materno(apelido_materno);
        this.setUsuarioxm(usuarioxm);
        this.setCorreobbva(correobbva);
        this.setArea_laboral(area_laboral);
        this.nombre = name;
        this.setUbicacion(ubicacion);
        this.setEdificio(edificio);
        this.setCelular_personal(celular_personal);
        this.setCorreo_personal(correo_personal);
        this.setProyecto_asignado(proyecto_asignado);
        this.setExpertise(expertise);
        this.setPrimer_nivel(primer_nivel);
        this.setSegundo_nivel(segundo_nivel);
        this.setTercer_nivel(tercer_nivel);
        this.idDrawable = getDrawable();
    }

    public ColaboradorModel(Parcel parcel) {
        this.setId(parcel.readInt());
        this.setNo_empleado(parcel.readInt());
        this.setPerfil(parcel.readString());
        this.setApellido_paterno(parcel.readString());
        this.setApelido_materno(parcel.readString());
        this.setUsuarioxm(parcel.readString());
        this.setCorreobbva(parcel.readString());
        this.setArea_laboral(parcel.readString());
        this.nombre = parcel.readString();
        this.setUbicacion(parcel.readString());
        this.setEdificio(parcel.readString());
        this.setCelular_personal(parcel.readLong());
        this.setCorreo_personal(parcel.readString());
        this.setProyecto_asignado(parcel.readString());
        this.setExpertise(parcel.readString());
        this.setPrimer_nivel(parcel.readInt());
        this.setSegundo_nivel(parcel.readInt());
        this.setTercer_nivel(parcel.readInt());
        this.idDrawable = getDrawable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(no_empleado);
        dest.writeString(perfil);
        dest.writeString(apellido_paterno);
        dest.writeString(apelido_materno);
        dest.writeString(usuarioxm);
        dest.writeString(correobbva);
        dest.writeString(area_laboral);
        dest.writeString(nombre);
        dest.writeString(ubicacion);
        dest.writeString(edificio);
        dest.writeLong(celular_personal);
        dest.writeString(correo_personal);
        dest.writeString(proyecto_asignado);
        dest.writeString(expertise);
        dest.writeInt(primer_nivel);
        dest.writeInt(segundo_nivel);
        dest.writeInt(tercer_nivel);
    }

    public static final Creator<ColaboradorModel> CREATOR = new Creator<ColaboradorModel>() {
        @Override
        public ColaboradorModel[] newArray(int size) {
            return new ColaboradorModel[size];
        }

        @Override
        public ColaboradorModel createFromParcel(Parcel source) {
            return new ColaboradorModel(source);
        }
    };

    public int getPrimer_nivel() {
        return primer_nivel;
    }

    public void setPrimer_nivel(int primer_nivel) {
        this.primer_nivel = primer_nivel;
    }

    public int getSegundo_nivel() {
        return segundo_nivel;
    }

    public void setSegundo_nivel(int segundo_nivel) {
        this.segundo_nivel = segundo_nivel;
    }

    public int getTercer_nivel() {
        return tercer_nivel;
    }

    public void setTercer_nivel(int tercer_nivel) {
        this.tercer_nivel = tercer_nivel;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getProyecto_asignado() {
        return proyecto_asignado;
    }

    public void setProyecto_asignado(String proyecto_asignado) {
        this.proyecto_asignado = proyecto_asignado;
    }

    public String getCorreo_personal() {
        return correo_personal;
    }

    public void setCorreo_personal(String correo_personal) {
        this.correo_personal = correo_personal;
    }

    public long getCelular_personal() {
        return celular_personal;
    }

    public void setCelular_personal(long celular_personal) {
        this.celular_personal = celular_personal;
    }

    public String getArea_laboral() {
        return area_laboral;
    }

    public void setArea_laboral(String area_laboral) {
        this.area_laboral = area_laboral;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    private int idDrawable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_empleado() {
        return no_empleado;
    }

    public void setNo_empleado(int no_empleado) {
        this.no_empleado = no_empleado;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApelido_materno() {
        return apelido_materno;
    }

    public void setApelido_materno(String apelido_materno) {
        this.apelido_materno = apelido_materno;
    }

    public String getCorreobbva() {
        return correobbva;
    }

    public void setCorreobbva(String correobbva) {
        this.correobbva = correobbva;
    }

    public String getUsuarioxm() {
        return usuarioxm;
    }

    public void setUsuarioxm(String usuarioxm) {
        this.usuarioxm = usuarioxm;
    }

    public String getName() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    private int getDrawable() {
        return R.drawable.ic_login_account;
    }
}