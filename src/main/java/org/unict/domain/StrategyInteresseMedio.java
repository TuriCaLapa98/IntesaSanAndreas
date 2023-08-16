package org.unict.domain;

public class StrategyInteresseMedio implements StrategyInteresse{
    @Override
    public double calcolaInteresse(double importo, int numeroRate) {
        return ((importo/numeroRate)+((importo*numeroRate*0.05)/1200));
    }
}
