package org.unict.domain;

public class StrategyInteresseAlto implements StrategyInteresse{
    @Override
    public float calcolaInteresse(float importo, int numeroRate) {
        return (float) ((importo/numeroRate)+((importo*numeroRate*0.1)/1200));
    }
}
