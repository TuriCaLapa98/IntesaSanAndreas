package org.unict.domain;

public class Prelievo extends OperazioneBancaria
{
    public Prelievo(String nomeOP, float importo, String iban)
    {
        super (nomeOP, importo, iban);
    }
    public Prelievo(String id, String nomeOP, float importo, String data, String iban)
    {
        super (id, nomeOP, importo, data, iban);
    }
}
