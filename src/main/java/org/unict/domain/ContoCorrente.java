package org.unict.domain;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ContoCorrente
{
    private String cf;
    private String iban;
    private float saldo;
    private String numeroCarta;
    private LocalDate dataScadenza;
    private String pin;
    public Map<String, PrelievoBancomat> listaPrelieviBancomat;
    public Map<String, Prelievo> listaPrelievi;
    public Map<String, Deposito> listaDepositi;


    public ContoCorrente(String iban, String cf) throws FileNotFoundException {
        this.cf=cf;
        this.iban = iban;
        this.saldo = 1000;
        this.numeroCarta =generaNumeroCarta();
        this.dataScadenza = LocalDate.now().plusYears(10);
        this.pin = generaPin();
        this.listaPrelieviBancomat = new HashMap<>();
        this.listaPrelievi = new HashMap<>();
        this.listaDepositi = new HashMap<>();
        caricaOperazioniBancarie();
    }

    public ContoCorrente(String iban, String cf, float saldo, String numeroCarta, String dataScadenza, String pin) throws FileNotFoundException {
        this.cf = cf;
        this.iban = iban;
        this.saldo = saldo;
        this.numeroCarta = numeroCarta;
        this.dataScadenza = LocalDate.parse(dataScadenza);
        this.pin = pin;
        this.listaPrelieviBancomat = new HashMap<>();
        this.listaPrelievi = new HashMap<>();
        this.listaDepositi = new HashMap<>();
        caricaOperazioniBancarie();
    }


    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String generaNumeroCarta()
    {
        StringBuilder builder;
        builder = new StringBuilder(16);

        for (int m = 0; m < 16; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

    private String generaPin()
    {
        StringBuilder builder;
        builder = new StringBuilder(4);

        for (int m = 0; m < 4; m++)
        {
            builder.append((int)(Math.random()*9));
        }
        return builder.toString();
    }

    public void caricaOperazioniBancarie()
    {
        try {
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\operazioniBancarie.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String iban = fp.readLine(); iban != null; iban = fp.readLine() )
            {
                if (iban.equals(this.iban))
                {
                    String nomeOP = fp.readLine();
                    switch (nomeOP)
                    {
                        case "PrelievoBancomat": String id = fp.readLine();
                                                    int codiceBancomat = Integer.parseInt(fp.readLine());
                                                    float importo = Float.parseFloat(fp.readLine());
                                                    String data = fp.readLine();

                                                    PrelievoBancomat prelievoBancomat = new PrelievoBancomat(id, nomeOP, importo, data, iban, codiceBancomat);
                                                    this.listaPrelieviBancomat.put(prelievoBancomat.getId(), prelievoBancomat);

                                                    if (this.listaPrelieviBancomat == null)
                                                        throw new Exception("Errore caricamento operazioni bancarie dei bancomat");
                                                break;

                        case "Prelievo": String id2 = fp.readLine();
                                            float importo2 = Float.parseFloat(fp.readLine());
                                            String data2 = fp.readLine();

                                            Prelievo prelievo = new Prelievo(id2, nomeOP, importo2, data2, iban);
                                            this.listaPrelievi.put(prelievo.getId(), prelievo);

                                            if (this.listaPrelieviBancomat == null)
                                                throw new Exception("Errore caricamento operazioni bancarie dei bancomat");
                                        break;

                        case "Deposito": String id3 = fp.readLine();
                                            float importo3 = Float.parseFloat(fp.readLine());
                                            String nomeMittente = fp.readLine();
                                            String cognomeMittente = fp.readLine();
                                            String data3 = fp.readLine();

                                            Deposito deposito = new Deposito(id3, nomeOP, importo3, data3, iban, nomeMittente, cognomeMittente);
                                            this.listaDepositi.put(deposito.getId(), deposito);

                                            if (this.listaPrelieviBancomat == null)
                                                throw new Exception("Errore caricamento operazioni bancarie dei bancomat");
                                        break;

                        default: break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}