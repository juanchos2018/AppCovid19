package com.apk.appinfocovid19.Activity;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DonacionActivityTest {

    @Test
    public void validarCampo_esCorrecto() {
        String testDato = "Maria";
        DonacionActivity donacion = new DonacionActivity();
        Assert.assertThat(String.format("La validacion del campo NombreDonante es correcta para %s ", testDato),
                donacion.ValidarCampo(testDato), is(true));
    }

    @Test
    public void validarCampo_esIncorrecto() {
        int testDato = 1;
        //boolean testDato = false;
        DonacionActivity donacion = new DonacionActivity();
        Assert.assertThat(String.format("La validacion del campo NombreDonante fallo para %s ", testDato),
                donacion.ValidarCampo(String.valueOf(testDato)), is(false));
    }

    @Test
    public void enviarData() {
        assertEquals(4, 2 + 2);
    }
}