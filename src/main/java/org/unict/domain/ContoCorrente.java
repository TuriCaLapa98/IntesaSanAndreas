package org.unict.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ContoCorrente
{
    private String cf;
    private String iban;
    private float saldo;
    private String numeroCarta;
    private LocalDate dataScadenza;
    private String pin;
    public Map<String, OperazioneBancaria> listaOperazioniBancarie;


    public ContoCorrente(String iban, String cf)
    {
        this.cf=cf;
        this.iban = iban;
        this.saldo = 1000;
        this.numeroCarta =generaNumeroCarta();
        this.dataScadenza = LocalDate.now().plusYears(10);
        this.pin = generaPin();
        this.listaOperazioniBancarie = new HashMap<>();
        caricaOperazioniBancarie();
    }

    public ContoCorrente(String iban, String cf, float saldo, String numeroCarta, String dataScadenza, String pin) {
        this.cf = cf;
        this.iban = iban;
        this.saldo = saldo;
        this.numeroCarta = numeroCarta;
        this.dataScadenza = LocalDate.parse(dataScadenza);
        this.pin = pin;
        this.listaOperazioniBancarie = new HashMap<>();
        caricaOperazioniBancarie();
    }


    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String generaNumeroCarta()
    {
        StringBuilder builder;
        builder = new StringBuilder(16);

        for (int m = 0; m < 16; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

    private String generaPin()
    {
        StringBuilder builder;
        builder = new StringBuilder(4);

        for (int m = 0; m < 4; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

    public void caricaOperazioniBancarie()
    {

    }
}