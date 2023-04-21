package org.unict.domain;

import java.time.LocalDate;

public abstract class OperazioneBancaria
{
    protected String id;
    protected String iban;
    protected String nomeOP;
    protected float importo;
    protected LocalDate data;

    public OperazioneBancaria(String nomeOP, float importo, String iban)
    {
        this.id = generaID();
        this.nomeOP = nomeOP;
        this.importo = importo;
        this.data = LocalDate.now();
        this.iban = iban;
    }

    public OperazioneBancaria(String id, String nomeOP, float importo, String data, String iban)
    {
        this.id = id;
        this.nomeOP = nomeOP;
        this.importo = importo;
        this.data = LocalDate.parse(data);
        this.iban = iban;
    }

    public OperazioneBancaria() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeOP() {
        return nomeOP;
    }

    public void setNomeOP(String nomeOP) {
        this.nomeOP = nomeOP;
    }

    public float getImporto() {
        return importo;
    }

    public void setImporto(float importo) {
        this.importo = importo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String generaID()
    {
        StringBuilder builder;
        builder = new StringBuilder(5);

        for (int m = 0; m < 5; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

}

