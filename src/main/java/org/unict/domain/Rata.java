package org.unict.domain;
import java.time.LocalDate;

public class Rata
{
    private String id;
    private String idServizioBancario;
    private LocalDate data;
    private double importo;

    public Rata(String idServizioBancario, LocalDate data, double importo) {
        this.id = generaID();
        this.idServizioBancario = idServizioBancario;
        this.data = data;
        this.importo = importo;
    }
    public Rata(String id, String idServizioBancario, LocalDate data, double importo) {
        this.id = id;
        this.idServizioBancario = idServizioBancario;
        this.data = data;
        this.importo = importo;
    }
    public String generaID()
    {
        StringBuilder builder;
        builder = new StringBuilder(10);

        for (int m = 0; m < 10; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getIdServizioBancario() {
        return idServizioBancario;
    }

    public void setIdServizioBancario(String idServizioBancario) {
        this.idServizioBancario = idServizioBancario;
    }

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public double getImporto() {return importo;}

    public void setImporto(double importo) {this.importo = importo;}

}