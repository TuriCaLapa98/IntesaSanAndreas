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
            String file = "D:/OneDrive - Università degli Studi di Catania/Magistrale/Primo Anno/Ingegneria del Software/Esame/Progetto/IntesaSanAndreas/src/main/java/org/unict/domain/Filetxt/elencoBanconote.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            System.out.println("\nSTO STAMPANDO LE BANCONOTE CHE CI SONO NEL BANCOMAT " + this.codice);

            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {

                int codiceBanconota = Integer.parseInt(fp.readLine());
                int numeroPezzi = Integer.parseInt(fp.readLine());

                if(Integer.parseInt(s) == this.codice)
                {
                    System.out.println("Codice Bancomat: " + this.codice + " Codice Banconota: " + codiceBanconota + " Numero Pezzi: " + numeroPezzi);

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

    public void calcolaPrelievo(int prelievo)
    {
        boolean operazione = false;
        int pz200 = 0, pz100 = 0, pz50 = 0, pz20 = 0, pz10 = 0, pz5 = 0;

        System.out.println("HO FINITO DI CALCOLARE LE BANCONOTE DA DARTI");
        System.out.println("Il totale è:");

        /** ---------- Pz taglio 200 ----------- **/
        int tmpPz1;
        int tmpPz2 = prelievo / 200;

        if(tmpPz2 <= this.listaBanconote.get(200).getNumPezzi())
        {
            pz200 = tmpPz2;
            System.out.println(pz200 + " Banconote da 200;");
        }
        else
        {
            pz200 = listaBanconote.get(200).getNumPezzi();
            System.out.println(pz200 + " Banconote da 200;");

            /** ---------- Pz taglio 100 ----------- **/
            tmpPz1 = prelievo - (pz200*200);
            tmpPz2 = tmpPz1/100;

            if (tmpPz2 <= listaBanconote.get(100).getNumPezzi())
            {
                pz100 = tmpPz2;
                System.out.println(pz100 + " Banconote da 100;");
            } else
            {
                pz100 = listaBanconote.get(100).getNumPezzi();
                System.out.println(pz100 + " Banconote da 100;");

                /** ---------- Pz taglio 50 ----------- **/
                tmpPz1 = tmpPz1 - (pz100*100);
                tmpPz2 = tmpPz1/50;

                if (tmpPz2 <= listaBanconote.get(50).getNumPezzi())
                {
                    pz50 = tmpPz2;
                    System.out.println(pz50 + " Banconote da 50;");
                } else
                {
                    pz50 = listaBanconote.get(50).getNumPezzi();
                    System.out.println(pz50 + " Banconote da 50;");

                    /** ---------- Pz taglio 20 ----------- **/
                    tmpPz1 = tmpPz1 - (pz50*50);
                    tmpPz2 = tmpPz1/20;

                    if (tmpPz2 <= listaBanconote.get(20).getNumPezzi())
                    {
                        pz20 = tmpPz2;
                        System.out.println(pz20 + " Banconote da 20;");
                    } else
                    {
                        pz20 = listaBanconote.get(20).getNumPezzi();
                        System.out.println(pz20 + " Banconote da 20;");

                        /** ---------- Pz taglio 10 ----------- **/
                        tmpPz1 = tmpPz1 - (pz20*20);
                        tmpPz2 = tmpPz1/10;

                        if (tmpPz2 <= listaBanconote.get(10).getNumPezzi())
                        {
                            pz10 = tmpPz2;
                            System.out.println(pz10 + " Banconote da 10;");
                        } else
                        {
                            pz10 = listaBanconote.get(10).getNumPezzi();
                            System.out.println(pz10 + " Banconote da 10;");

                            /** ---------- Pz taglio 5 ----------- **/
                            tmpPz1 = tmpPz1 - (pz10*10);
                            tmpPz2 = tmpPz1/5;

                            if (tmpPz2 <= listaBanconote.get(5).getNumPezzi())
                            {
                                pz5 = tmpPz2;
                                System.out.println(pz5 + " Banconote da 5;");
                            } else
                            {
                                pz5 = listaBanconote.get(5).getNumPezzi();
                                System.out.println(pz5 + " Banconote da 5;\n");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\n");

        riduciPezzi(pz5,pz10,pz20,pz50,pz100,pz200);
    }

    public void riduciPezzi(int pz5, int pz10, int pz20, int pz50, int pz100, int pz200)
    {


        Banconota b5 = new Banconota(this.codice,5,(listaBanconote.get(5).getNumPezzi() - pz5));
        this.listaBanconote.replace(5, listaBanconote.get(5), b5);
        Banconota b10 = new Banconota(this.codice,10, (listaBanconote.get(10).getNumPezzi() - pz10));
        this.listaBanconote.replace(10, listaBanconote.get(10), b10);
        Banconota b20 = new Banconota(this.codice,20, (listaBanconote.get(20).getNumPezzi() - pz20));
        this.listaBanconote.replace(20, listaBanconote.get(20), b20);
        Banconota b50 = new Banconota(this.codice,50, (listaBanconote.get(50).getNumPezzi() - pz50));
        this.listaBanconote.replace(50, listaBanconote.get(50), b50);
        Banconota b100 = new Banconota(this.codice,100, (listaBanconote.get(100).getNumPezzi() - pz100));
        this.listaBanconote.replace(100, listaBanconote.get(100), b100);
        Banconota b200 = new Banconota(this.codice,200, (listaBanconote.get(200).getNumPezzi() - pz200));
        this.listaBanconote.replace(200, listaBanconote.get(200), b200);

        //Observer Pattern
        this.setChanged();
        this.notifyObservers();
    }
}
