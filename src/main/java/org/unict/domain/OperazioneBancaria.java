package org.unict.domain;

import java.time.LocalDate;

public class OperazioneBancaria
{
    private String id;
    private String nomeOP;
    private float importo;
    private LocalDate data;

    public OperazioneBancaria(String nomeOP, float importo)
    {
        this.id = generaID();
        this.nomeOP = nomeOP;
        this.importo = importo;
        this.data = LocalDate.now();
    }

    public OperazioneBancaria(String id, String nomeOP, float importo, String data)
    {
        this.id = id;
        this.nomeOP = nomeOP;
        this.importo = importo;
        this.data = LocalDate.parse(data);
    }

    public OperazioneBancaria() {

    }

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

