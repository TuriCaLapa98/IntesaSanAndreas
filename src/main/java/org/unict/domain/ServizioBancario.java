package org.unict.domain;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class ServizioBancario
{
    private String id;
    private String iban;
    private float importo;
    private LocalDate data;
    private boolean attivo;
    private LocalDate dataScadenza;
    private int numeroRate;
    private float valoreRata;
    private String tipologia;
    private float interesse;
    public Map<String,Rata> listaRate;

    public ServizioBancario(String iban, float importo, LocalDate dataScadenza, int numeroRate, float valoreRata, String tipologia)
    {
        this.id = generaID();
        this.iban = iban;
        this.importo = importo;
        this.data = LocalDate.now();
        this.attivo = true;
        this.dataScadenza = dataScadenza;
        this.numeroRate = numeroRate;
        this.valoreRata = valoreRata;
        this.tipologia = tipologia;
        this.interesse = 0;
        this.listaRate = new HashMap<>();
        caricaRate();
    }

    public ServizioBancario(String id, String iban, float importo,LocalDate data, LocalDate dataScadenza, boolean attivo, int numeroRate, float valoreRata, String tipologia, float interesse) throws Exception {
        this.id = id;
        this.iban = iban;
        this.importo = importo;
        this.data = data;
        this.attivo = attivo;
        this.dataScadenza = dataScadenza;
        this.numeroRate = numeroRate;
        this.valoreRata = valoreRata;
        this.tipologia = tipologia;
        this.interesse = interesse;
        this.listaRate = new HashMap<>();
        if (interesse == 1F) setValoreRata(calcolaInteresse());
        caricaRate();
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public float getValoreRata() {
        return valoreRata;
    }

    public void setValoreRata(float valoreRata) {
        this.valoreRata = valoreRata;
    }

    public float getInteresse() {
        return interesse;
    }

    public void setInteresse(float interesse) {
        this.interesse = interesse;
    }

    public float calcolaInteresse() throws Exception {
        GestioneInteresse gest = new GestioneInteresse();
        gest.setStrategyInteresseFloat(interesse);
        switch (tipologia)
        {
            case "Prestito": return gest.calcola(importo, 48);

            case "Mutuo": return gest.calcola(importo, 120);

            default: return 0;
        }
    }

    public void caricaRate()
    {
        try {
            String file = FilePaths.ELENCO_RATE_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String id = fp.readLine(); id != null; id = fp.readLine() )
            {
                if (id.equals(this.id))
                {
                    String idRata = fp.readLine();
                    LocalDate data = LocalDate.parse(fp.readLine());
                    float importo = Float.parseFloat(fp.readLine());

                    Rata rata = new Rata(idRata, id, data, importo);
                    this.listaRate.put(rata.getId(), rata);

                    if(this.listaRate == null)
                        throw new Exception("Errore caricamento Servizi Bancari");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
