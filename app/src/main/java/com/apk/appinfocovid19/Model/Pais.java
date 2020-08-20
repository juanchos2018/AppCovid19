package com.apk.appinfocovid19.Model;

public class Pais {
    private int IdPais;
    private String nombrePais;
    private double latitud;
    private double longitud;
    private int casosConfirmados;
    private int bajasTotales;
    private int casosRecuperadosTotales;

    public int getIdPais() {
        return IdPais;
    }

    public void setIdPais(int idPais) {
        IdPais = idPais;
    }

    public int getCasosConfirmados() {
        return casosConfirmados;
    }

    public void setCasosConfirmados(int casosConfirmados) {
        this.casosConfirmados = casosConfirmados;
    }

    public int getBajasTotales() {
        return bajasTotales;
    }

    public void setBajasTotales(int bajasTotales) {
        this.bajasTotales = bajasTotales;
    }

    public int getCasosRecuperadosTotales() {
        return casosRecuperadosTotales;
    }

    public void setCasosRecuperadosTotales(int casosRecuperadosTotales) {
        this.casosRecuperadosTotales = casosRecuperadosTotales;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
