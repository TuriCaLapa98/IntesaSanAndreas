package org.unict.domain;

public class StrategyInteresseMedio implements StrategyInteresse{
    @Override
    public float calcolaInteresse(float importo, int numeroRate) {
        return (float) ((importo/numeroRate)+((importo*numeroRate*0.05)/1200));
    }
}
