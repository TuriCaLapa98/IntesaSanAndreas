package org.unict.domain;

public class PrelievoBancomat extends OperazioneBancaria
{
    private int codiceBancomat;

    public PrelievoBancomat(String nomeOP, float importo, String iban, int codiceBancomat)
    {
        super (nomeOP, importo, iban);
        this.codiceBancomat = codiceBancomat;
    }
    public PrelievoBancomat(String id, String nomeOP, float importo, String data, String iban, int codiceBancomat)
    {
        super (id, nomeOP, importo, data, iban);
        this.codiceBancomat = codiceBancomat;
    }

    public int getCodiceBancomat() {
        return codiceBancomat;
    }

    public void setCodiceBancomat(int codiceBancomat) {
        this.codiceBancomat = codiceBancomat;
    }
}
