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

    public void caricaListaBanconote(int codiceBancomat) {
        try {
            String file = "D:/OneDrive - Università degli Studi di Catania/Magistrale/Primo Anno/Ingegneria del Software/Esame/Progetto/IntesaSanAndreas/src/main/java/org/unict/domain/Filetxt/elencoBanconote.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            System.out.println("STO STAMPANDO LE BANCONOTE CHE CI SONO NEL BANCOMAT\n");

            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {

                int codiceBanconota = Integer.parseInt(fp.readLine());
                int numeroPezzi = Integer.parseInt(fp.readLine());

                if(Integer.parseInt(s) == codiceBancomat)
                {
                    System.out.println("Codice Bancomat: " + codiceBancomat + " Codice Banconota: " + codiceBanconota + " Numero Pezzi: " + numeroPezzi);

                    Banconota b = new Banconota(codiceBanconota, numeroPezzi);

                    listaBanconote.put(codiceBancomat, b);
                    if (this.listaBanconote == null)
                        throw new Exception("Errore caricamento Banconote");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Funzione da richiamare dal dipendente tecnico quando fa l'update delle banconote
     * UC 10
     * */
    /*public void stampaListaBanconote()
    {
        try {
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoCc.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String cf = fp.readLine(); Objects.equals(cf, this.cf); cf = fp.readLine())
            {
                String iban = fp.readLine();
                float saldo = Float.parseFloat(fp.readLine());
                String numeroCarta = fp.readLine();
                String dataScadenza = fp.readLine();
                String pin = fp.readLine();

                ContoCorrente contoCorrente = new ContoCorrente(iban, cf, saldo, numeroCarta, dataScadenza, pin);
                this.listaContiCorrente.put(iban, contoCorrente);
                if(this.listaContiCorrente == null)
                    throw new Exception("Errore caricamento clienti");
            }
        } catch (Exception e) {e.printStackTrace();}

    }*/

    public void calcolaPrelievo(int prelievo)
    {
        boolean operazione = false;
        int pz200 = 0, pz100 = 0, pz50 = 0, pz20 = 0, pz10 = 0, pz5 = 0;

        //Pz taglio 200
        int tmpPz1 = prelievo/200;
        int tmpPz2;

        if(tmpPz1<=listaBanconote.get(200).getNumPezzi())
        {
            pz200=tmpPz1;
        } else {
            pz200 = listaBanconote.get(200).getNumPezzi();
            tmpPz2 = tmpPz1 - listaBanconote.get(200).getNumPezzi();

            //Pz taglio 100
            if (tmpPz2 <= listaBanconote.get(100).getNumPezzi()) {
                pz100 = tmpPz2;
            } else {
                pz100 = listaBanconote.get(100).getNumPezzi();
                tmpPz1 = tmpPz2 - listaBanconote.get(100).getNumPezzi();

                //Pz taglio 50
                if (tmpPz1 <= listaBanconote.get(50).getNumPezzi()) {
                    pz50 = tmpPz1;
                } else {
                    pz50 = listaBanconote.get(50).getNumPezzi();
                    tmpPz2 = tmpPz1 - listaBanconote.get(50).getNumPezzi();

                    //Pz taglio 20
                    if (tmpPz2 <= listaBanconote.get(20).getNumPezzi()) {
                        pz20 = tmpPz2;
                    } else {
                        pz20 = listaBanconote.get(20).getNumPezzi();
                        tmpPz1 = tmpPz2 - listaBanconote.get(20).getNumPezzi();

                        //Pz taglio 10
                        if (tmpPz1 <= listaBanconote.get(10).getNumPezzi()) {
                            pz10 = tmpPz1;
                        } else {
                            pz10 = listaBanconote.get(10).getNumPezzi();
                            tmpPz2 = tmpPz1 - listaBanconote.get(10).getNumPezzi();

                            //Pz taglio 5
                            if (tmpPz2 <= listaBanconote.get(5).getNumPezzi()) {
                                pz5 = tmpPz2;
                            } else {
                                pz5 = listaBanconote.get(5).getNumPezzi();
                                tmpPz1 = tmpPz2 - listaBanconote.get(5).getNumPezzi();
                            }
                        }
                    }
                }
            }
        }
        riduciPezzi(pz5,pz10,pz20,pz50,pz100,pz200);
    }

    private void riduciPezzi(int pz5, int pz10, int pz20, int pz50, int pz100, int pz200)
    {
        Banconota b5 = new Banconota(5,listaBanconote.get(5).getNumPezzi() - pz5);
        listaBanconote.replace(5, listaBanconote.get(5), b5);
        Banconota b10 = new Banconota(5,listaBanconote.get(10).getNumPezzi() - pz10);
        listaBanconote.replace(10, listaBanconote.get(10), b10);
        Banconota b20 = new Banconota(20,listaBanconote.get(20).getNumPezzi() - pz20);
        listaBanconote.replace(20, listaBanconote.get(20), b20);
        Banconota b50 = new Banconota(50,listaBanconote.get(50).getNumPezzi() - pz50);
        listaBanconote.replace(50, listaBanconote.get(50), b50);
        Banconota b100 = new Banconota(100,listaBanconote.get(100).getNumPezzi() - pz100);
        listaBanconote.replace(100, listaBanconote.get(100), b100);
        Banconota b200 = new Banconota(200,listaBanconote.get(200).getNumPezzi() - pz200);
        listaBanconote.replace(200, listaBanconote.get(200), b200);

        //Observer Pattern
        this.setChanged();
        this.notifyObservers();
    }
}
