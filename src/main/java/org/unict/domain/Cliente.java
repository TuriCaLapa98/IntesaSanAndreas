package org.unict.domain;

import java.io.*;
import java.util.*;

public class Cliente
{
    private String cf;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String email;
    private String telefono;
    private float prelievoGiornaliero; //prelievo corrente che verra decrementato = 1500;
    private boolean attivo;
    private int valorePrelievo = 1500;
    private Map <String, ContoCorrente> listaContiCorrente;

    public Cliente(String cf, String nome, String cognome, String dataNascita, String email, String telefono)
    {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.telefono = telefono;
        this.prelievoGiornaliero = valorePrelievo;
        this.listaContiCorrente=new HashMap<>();
        this.attivo = true;
        caricaCc();
    }

    /* ------- Get & Set ------- */

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getPrelievoGiornaliero() {
        return prelievoGiornaliero;
    }

    public void setPrelievoGiornaliero(float prelievoGiornaliero) {
        this.prelievoGiornaliero = prelievoGiornaliero;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;}

    public int getValorePrelievo() {
        return valorePrelievo;
    }

    public void setValorePrelievo(int valorePrelievo)
    {
        this.valorePrelievo = valorePrelievo;
    }

    public void createListaCcCliente()
    {
        this.listaContiCorrente = new HashMap<>();
    }

    public void creaContoCorrente() throws IOException {
        String IBAN = generaIban();
        ContoCorrente contoCorrente =new ContoCorrente(IBAN,this.cf);
        BancaISA bancaISA = BancaISA.getInstance();
        this.listaContiCorrente.put(IBAN,contoCorrente);
        bancaISA.listaCc.put(IBAN,contoCorrente);

        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoCc.txt");
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);
        bancaISA.listaCc.forEach((key, value) -> printout.println
                (value.getCf()
                        + "\n" + key
                        + "\n" + value.getSaldo()
                        + "\n" + value.getNumeroCarta()
                        + "\n" + value.getDataScadenza()
                        + "\n" + value.getPin()));

        printout.flush();
        printout.close();

        //contoCorrente.dettagliContoCorrente();
    }

    public String generaIban()
    {
        StringBuilder builder;

        builder = new StringBuilder(22);

        for (int m = 0; m < 22; m++) {
            builder.append((int)(Math.random()*9));
        }

        return  "IT88A" + builder.toString();
    }

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
}
