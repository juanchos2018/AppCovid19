package com.apk.appinfocovid19.Model;

public class Diagnostico {
    String temperaturaCorporal;
    String congestionNasal;
    String indigestionEstomacal;
    String tieneTos;
    String fatiga;
    String faltaAlimento;
    String perdidaGusto;

    public String getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public void setTemperaturaCorporal(String temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public String getCongestionNasal() {
        return congestionNasal;
    }

    public void setCongestionNasal(String sentirseAhora) {
        this.congestionNasal = congestionNasal;
    }

    public String getIndigestionEstomacal() {
        return indigestionEstomacal;
    }

    public void setIndigestionEstomacal(String tienefiebre) { this.indigestionEstomacal = indigestionEstomacal; }

    public String getTieneTos() {
        return tieneTos;
    }

    public void setTieneTos(String tieneTos) {
        this.tieneTos = tieneTos;
    }

    public String getFatiga() {
        return fatiga;
    }

    public void setFatiga(String fatiga) {
        this.fatiga = fatiga;
    }

    public String getFaltaAlimento() {
        return faltaAlimento;
    }

    public void setFaltaAlimento(String faltaAlimento) {
        this.faltaAlimento = faltaAlimento;
    }

    public String getPerdidaGusto() {
        return perdidaGusto;
    }

    public void setPerdidaGusto(String dondeEstaAhora) {
        this.perdidaGusto = perdidaGusto;
    }
}
