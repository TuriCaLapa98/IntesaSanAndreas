package org.unict.domain;

public class StrategyInteresseVariabile implements StrategyInteresse {
    @Override
    public float calcolaInteresse(float importo, int numeroRate) {
        double interesseVariabile = 0.05 + (Math.random() * 0.08);
        return (float) ((importo/numeroRate)+((importo*numeroRate*interesseVariabile)/1200));
    }
}
