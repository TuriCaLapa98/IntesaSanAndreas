package org.unict.domain;

public class StrategyInteresseAlto implements StrategyInteresse{
    @Override
    public double calcolaInteresse(double importo, int numeroRate) {
        return ((importo/numeroRate)+((importo*numeroRate*0.1)/1200));
    }
}
