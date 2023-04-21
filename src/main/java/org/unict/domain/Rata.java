package org.unict.domain;
import java.time.LocalDate;

public class Rata
{
    private int id;
    private LocalDate data;
    private double importo;
    private float percentuale;

    public Rata(int id, LocalDate data, double importo, float percentuale) {
        this.id = id;
        this.data = data;
        this.importo = importo;
        this.percentuale = percentuale;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public double getImporto() {return importo;}

    public void setImporto(double importo) {this.importo = importo;}

    public float getPercentuale() {return percentuale;}

    public void setPercentuale(float percentuale) {this.percentuale = percentuale;}

}