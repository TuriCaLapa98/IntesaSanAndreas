package org.unict.domain;

public class Banconota {
    
    private int codice;
    
    private int numPezzi;

    public Banconota(int codiceBanconota, int numeroPezzi) {
        this.codice = codiceBanconota;
        this.numPezzi = numeroPezzi;
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
