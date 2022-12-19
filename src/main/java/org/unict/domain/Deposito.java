package org.unict.domain;

public class Deposito extends OperazioneBancaria
{
    private String nomeMittente;
    private String cognomeMittente;

    public Deposito(String nomeOP, float importo, String iban, String nomeMittente, String cognomeMittente)
    {
        super (nomeOP, importo, iban);
        this.nomeMittente = nomeMittente;
        this.cognomeMittente = cognomeMittente;
    }
    public Deposito(String id, String nomeOP, float importo, String data, String iban, String nomeMittente, String cognomeMittente)
    {
        super (id, nomeOP, importo, data, iban);
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
