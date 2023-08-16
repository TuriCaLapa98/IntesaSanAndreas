package org.unict.domain;
import java.time.LocalDate;


public class ServizioBancario
{
    private String id;
    private float importo;
    private LocalDate data;
    private boolean attivo;
    private LocalDate dataScadenza;
    private int numeroRate;
    //INSERIRE VALORE RATA
    private String tipologia;
    private StrategyInteresse strategyInteresse;

    public ServizioBancario(float importo, LocalDate dataScadenza, int numeroRate, String tipologia)
    {
        this.id = generaID();
        this.importo = importo;
        this.data = LocalDate.now();
        this.attivo = true;
        this.dataScadenza = dataScadenza;
        this.numeroRate = numeroRate;
        this.tipologia = tipologia;
        this.strategyInteresse = new StrategyInteresse() {
            @Override
            public double calcolaInteresse(double importo, int numRate) {
                return importo;
            }
        };
    }

    public String generaID()
    {
        StringBuilder builder;
        builder = new StringBuilder(5);

        for (int m = 0; m < 5; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getImporto() {
        return importo;
    }

    public void setImporto(float importo) {
        this.importo = importo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public int getNumeroRate() {
        return numeroRate;
    }

    public void setNumeroRate(int numeroRate) {
        this.numeroRate = numeroRate;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public StrategyInteresse getStrategyInteresse() {
        return strategyInteresse;
    }

    public void setStrategyInteresse(StrategyInteresse strategyInteresse) {
        this.strategyInteresse = strategyInteresse;
    }

    public double calcolaInteresse() {
        return strategyInteresse.calcolaInteresse(importo, numeroRate);
    }

}
