package org.unict.domain;

public class StrategyInteresseBasso implements StrategyInteresse{
    @Override
    public double calcolaInteresse(double importo, int numeroRate) {
        return ((importo/numeroRate)+((importo*numeroRate*0.02)/1200));
    }
}
