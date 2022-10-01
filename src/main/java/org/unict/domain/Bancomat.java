package org.unict.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class Bancomat {

    private Map<Integer, Banconota> listaBanconote;

    private int codice;

    private String posizione;

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

    public void caricaPezzi() {
        try {
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoBanconote.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (int codiceBancomat = Integer.parseInt(fp.readLine()); codiceBancomat != 0; codiceBancomat = Integer.parseInt(fp.readLine())) {
                int codiceBanconota = Integer.parseInt(fp.readLine());
                int numeroPezzi = Integer.parseInt(fp.readLine());

                Banconota b = new Banconota(codiceBanconota, numeroPezzi);
                this.listaBanconote.put(codiceBancomat, b);
                if (this.listaBanconote == null)
                    throw new Exception("Errore caricamento clienti");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stampaListaBanconote() {
        /**

         public void caricaCc()
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
         int pin = Integer.parseInt(fp.readLine());

         ContoCorrente contoCorrente = new ContoCorrente(iban, cf, saldo, numeroCarta, dataScadenza, pin);
         this.listaContiCorrente.put(iban, contoCorrente);
         if(this.listaContiCorrente == null)
         throw new Exception("Errore caricamento clienti");
         }
         } catch (Exception e) {
         e.printStackTrace();
         }
         }

         **/
    }

}
