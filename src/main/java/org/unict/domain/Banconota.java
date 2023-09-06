package org.unict.domain;

public class Banconota {

    private  int codiceBancomat;
    private int codice;
    private int numPezzi;

    public Banconota(int codiceBancomat, int codiceBanconota, int numeroPezzi) {
        this.codiceBancomat = codiceBancomat;
        this.codice = codiceBanconota;
        this.numPezzi = numeroPezzi;
    }

    public int getCodiceBancomat() {
        return codiceBancomat;
    }

    public void setCodiceBancomat(int codiceBancomat) {
        this.codiceBancomat = codiceBancomat;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getNumPezzi() {
        return numPezzi;
    }

    public void setNumPezzi(int numPezzi) {
        this.numPezzi = numPezzi;
    }
}
