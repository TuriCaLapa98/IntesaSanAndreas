package org.unict.domain;

public class GestioneInteresse {
    StrategyInteresse interesse;
    public void setStrategyInteresse(StrategyInteresse interest)
    {
        this.interesse = interest;
    }
    public void setStrategyInteresseFloat(float inters) throws Exception {
        //IN BASE AL PARAMETRO D'APPOGGIO "INTERESSE" RIESCO AD APPLICARE IL PATTERN STRATEGY AL CARICAMENTO DEL SERVIZIO
        if (inters == 0.02f)
        {
            this.interesse = new StrategyInteresseBasso();
        } else if (inters == 0.05f)
        {
            this.interesse = new StrategyInteresseMedio();
        } else if (inters == 0.1f)
        {
            this.interesse = new StrategyInteresseAlto();
        } else if (inters == 1F)
        {
            this.interesse = new StrategyInteresseVariabile();
        }else
        {
            this.interesse = new StrategyInteresse() {
                @Override
                public float calcolaInteresse(float importo, int numRate) {
                    return importo;
                }
            };
            throw new Exception("Errore nel rilevare l'interesse");
        }
    }
    public float calcola(float importo, int numeroRate)
    {
        return interesse.calcolaInteresse(importo, numeroRate);
    }
}
