package org.unict.domain;

import java.time.LocalDate;
import java.util.Date;

public class CartaDiBancomat
{
    private String numeroCarta;
    private LocalDate dataScadenza;
    private String pin;

    public CartaDiBancomat(String numeroCarta)
    {
        this.numeroCarta = numeroCarta;
        this.dataScadenza = LocalDate.now().plusYears(10);
        this.pin = generaPin();
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
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

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
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
}
