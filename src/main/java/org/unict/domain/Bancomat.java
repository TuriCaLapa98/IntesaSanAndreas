package org.unict.domain;


import java.io.*;
import java.util.*;
import java.util.Observable;

//Observer Pattern
public class Bancomat extends Observable {
    private int codice;
    private String posizione;
    public Map<Integer, Banconota> listaBanconote;


    public Bancomat(int codiceBancomat, String posizione)
    {
        this.codice = codiceBancomat;
        this.posizione = posizione;
        this.listaBanconote = new HashMap<>();
        caricaListaBanconote();
    }

    public HashMap<Integer, Banconota> getListaBanconote(){return (HashMap<Integer, Banconota>) this.listaBanconote;}

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void caricaListaBanconote() {
        try {
            String file = FilePaths.ELENCO_BANCONOTE_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {

                int codiceBanconota = Integer.parseInt(fp.readLine());
                int numeroPezzi = Integer.parseInt(fp.readLine());

                if(Integer.parseInt(s) == this.codice)
                {

                    Banconota b = new Banconota(this.codice, codiceBanconota, numeroPezzi);

                    this.listaBanconote.put(codiceBanconota, b);
                    if (this.listaBanconote == null)
                        throw new Exception("Errore caricamento Banconote");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcolaPrelievo(int prelievo) {
        int[] tagliBanconote = {200, 100, 50, 20, 10, 5};
        int[] pezziBanconote = new int[tagliBanconote.length];

        System.out.println("HO FINITO DI CALCOLARE LE BANCONOTE DA DARTI");
        System.out.println("Il totale è:");

        for (int i = 0; i < tagliBanconote.length; i++) {
            int numPezzi = this.listaBanconote.get(tagliBanconote[i]).getNumPezzi();
            pezziBanconote[i] = Math.min(prelievo / tagliBanconote[i], numPezzi);
            prelievo -= pezziBanconote[i] * tagliBanconote[i];
            System.out.println(pezziBanconote[i] + " Banconote da " + tagliBanconote[i] + ";");
        }
        System.out.println("\n");

        riduciPezzi(pezziBanconote[5], pezziBanconote[4], pezziBanconote[3], pezziBanconote[2], pezziBanconote[1], pezziBanconote[0]);
    }

    public void aggiornaPezziBanconota(int codiceBanconota) throws IOException {

        int numRicarica = (1000 / codiceBanconota);
        Banconota b = new Banconota( this.codice, codiceBanconota, numRicarica );
        this.listaBanconote.replace(codiceBanconota, b);
    }

    public void riduciPezzi(int pz5, int pz10, int pz20, int pz50, int pz100, int pz200)
    {

        Banconota b5 = new Banconota(this.codice,5,(this.listaBanconote.get(5).getNumPezzi() - pz5));
        this.listaBanconote.replace(5, this.listaBanconote.get(5), b5);
        Banconota b10 = new Banconota(this.codice,10, (this.listaBanconote.get(10).getNumPezzi() - pz10));
        this.listaBanconote.replace(10, this.listaBanconote.get(10), b10);
        Banconota b20 = new Banconota(this.codice,20, (this.listaBanconote.get(20).getNumPezzi() - pz20));
        this.listaBanconote.replace(20, this.listaBanconote.get(20), b20);
        Banconota b50 = new Banconota(this.codice,50, (this.listaBanconote.get(50).getNumPezzi() - pz50));
        this.listaBanconote.replace(50, this.listaBanconote.get(50), b50);
        Banconota b100 = new Banconota(this.codice,100, (this.listaBanconote.get(100).getNumPezzi() - pz100));
        this.listaBanconote.replace(100, this.listaBanconote.get(100), b100);
        Banconota b200 = new Banconota(this.codice,200, (this.listaBanconote.get(200).getNumPezzi() - pz200));
        this.listaBanconote.replace(200, this.listaBanconote.get(200), b200);

        //Observer Pattern
        this.setChanged();
        this.notifyObservers();
    }

}
