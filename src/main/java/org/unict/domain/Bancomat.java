package org.unict.domain;

import java.io.*;
import java.util.*;

public class Bancomat {
    private int codice;
    private String posizione;
    private Map<Integer, Banconota> listaBanconote;

    public Bancomat(int codiceBancomat, String posizione) {
    }

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
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoBanconote.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            System.out.println("STO STAMPANDO LE BANCONOTE CHE CI SONO NEL BANCOMAT\n");

            for (int codiceBancomat = Integer.parseInt(fp.readLine()); codiceBancomat != 0; codiceBancomat = Integer.parseInt(fp.readLine()))
            {
                int codiceBanconota = Integer.parseInt(fp.readLine());
                int numeroPezzi = Integer.parseInt(fp.readLine());

                System.out.println("Codice Bancomat: " + codiceBancomat + " Codice Banconota: " + codiceBanconota + " Numero Pezzi: " + numeroPezzi);

                Banconota b = new Banconota(codiceBanconota, numeroPezzi);
                this.listaBanconote.put(codiceBancomat, b);
                if (this.listaBanconote == null)
                    throw new Exception("Errore caricamento Banconote");
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

    public void calcolaPrelievo(int Prelievo)
    {
        //Il prelievo deve essere divisibile per 5. IMPLEMENTARE CONTROLLO DOPO ACQUISIZIONE PRELIEVO DA TASTIERA, dare la possibilità di uscire dal loop

        int pz200 = 0, pz100 = 0, pz50 = 0, pz20 = 0, pz10 = 0, pz5 = 0;
        /**if (listaCc.get(iban).getSaldo() >= prelievo) {
         //inziamo dal taglio più grande/ tmp quantita di banconote
         pz200 = prelievo/200; //dim200=20pz , pz200=25
         if(pz200 > max200 //prendere il valore della quantità dei pezzi da 200)
         {
         //anzichè usare il tmp usare direttamente il valore massimo
         tmp200=//max200;
         pz100=(max200*200-prelievo)/100 //pz100=5;
         prelievo = prelievo - max200*200;
         }

         pz100 = prelievo/100;
         //implementare taglio banconote: lista con valore
         System.out.println("\nPrelevare il denaro richiesto\n");
         }*/
    }

}
