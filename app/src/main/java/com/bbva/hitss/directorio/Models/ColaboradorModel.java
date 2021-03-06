package com.bbva.hitss.directorio.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bbva.R;

/**
 * Created by Hitss on 10/03/2016.
 */
public class ColaboradorModel implements Parcelable {
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

    public ColaboradorModel() {
    }

    public ColaboradorModel(Parcel parcel) {
        id = parcel.readInt();
        no_empleado = parcel.readInt();
        perfil = parcel.readString();
        apellido_paterno = parcel.readString();
        apelido_materno = parcel.readString();
        usuarioxm = parcel.readString();
        correobbva = parcel.readString();
        area_laboral = parcel.readString();
        nombre = parcel.readString();
        ubicacion = parcel.readString();
        edificio = parcel.readString();
        celular_personal = parcel.readLong();
        correo_personal = parcel.readString();
        proyecto_asignado = parcel.readString();
        expertise = parcel.readString();
        primer_nivel = parcel.readInt();
        segundo_nivel = parcel.readInt();
        tercer_nivel = parcel.readInt();
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

    @SuppressWarnings("unused")
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

    public void setName(String name) {
        this.nombre = name;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    private int getDrawable() {
        return R.drawable.ic_login_account;
    }
}