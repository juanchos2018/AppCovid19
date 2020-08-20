package com.apk.appinfocovid19.Model;

public class Noticias {

    String url_imagen;
    String descripcion;

    public  Noticias(){

    }

    public Noticias(String url_imagen, String descripcion) {
        this.url_imagen = url_imagen;
        this.descripcion = descripcion;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
