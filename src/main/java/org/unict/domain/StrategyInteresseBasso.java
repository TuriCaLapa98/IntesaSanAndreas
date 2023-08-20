package org.unict.domain;

public class StrategyInteresseBasso implements StrategyInteresse{
    @Override
    public float calcolaInteresse(float importo, int numeroRate) {
        return (float) ((importo/numeroRate)+((importo*numeroRate*0.02)/1200));
    }
}
