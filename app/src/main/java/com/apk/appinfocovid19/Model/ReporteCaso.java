package com.apk.appinfocovid19.Model;

public class ReporteCaso {

    String tipoCaso;
    String apellidoPaciente;
    String nombrePaciente;
    String sexo;
    int edad;
    int celular;
    String distrito;
    String direccion;
    String otrasobservaciones;
    String key_caso;
    String estado;


    String token;
    String latitud;
    String longitud;



    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }


    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOtrasObservaciones() {
        return otrasobservaciones;
    }

    public void setOtrasObservaciones(String otrasobservaciones) { this.otrasobservaciones = otrasobservaciones; }

    public String getKey_caso() {
        return key_caso;
    }

    public void setKey_caso(String key_caso) {
        this.key_caso = key_caso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
