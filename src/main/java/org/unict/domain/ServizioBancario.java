package org.unict.domain;
import java.time.LocalDate;


public abstract class ServizioBancario
{
    protected String id;
    protected float importo;
    protected LocalDate data;
    protected boolean attivo;
    protected LocalDate dataScadenza;
    protected int numeroRate;

    public ServizioBancario(String id, float importo, LocalDate data, boolean attivo, LocalDate dataScadenza, int numeroRate)
    {
        this.id = id;
        this.importo = importo;
        this.data = data;
        this.attivo = attivo;
        this.dataScadenza = dataScadenza;
        this.numeroRate = numeroRate;
    }

    public ServizioBancario(){}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public float getImporto() {return importo;}

    public void setImporto(float importo) {this.importo = importo;}

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public boolean isAttivo() {return attivo;}

    public void setAttivo(boolean attivo) {this.attivo = attivo;}

    public LocalDate getDataScadenza() {return dataScadenza;}

    public void setDataScadenza(LocalDate dataScadenza) {this.dataScadenza = dataScadenza;}

    public int getNumeroRate() {return numeroRate;}

    public void setNumeroRate(int numeroRate) {this.numeroRate = numeroRate;}
}
