package org.unict.domain;

import java.util.Date;

public class Deposito extends OperazioneBancaria{
    String nomeMittente;
    String cognomeMittente;

    public Deposito(String id, String nomeOP, float importo, Date data, String nomeMittente, String cognomeMittente) {
        super(id, nomeOP, importo, String.valueOf(data));

        this.nomeMittente = nomeMittente;
        this.cognomeMittente = cognomeMittente;
    }

    public Deposito(String nomeMittente, String cognomeMittente) {
        super();
        this.nomeMittente = nomeMittente;
        this.cognomeMittente = cognomeMittente;
    }

    public String getNomeMittente() {
        return nomeMittente;
    }

    public void setNomeMittente(String nomeMittente) {
        this.nomeMittente = nomeMittente;
    }

    public String getCognomeMittente() {
        return cognomeMittente;
    }

    public void setCognomeMittente(String cognomeMittente) {
        this.cognomeMittente = cognomeMittente;
    }
}
