package org.unict.domain;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ContoCorrente
{
    private static ContoCorrente conto;
    private String cf;
    private String iban;
    private float saldo;
    private String numeroCarta;
    private LocalDate dataScadenza;
    private String pin;
    public Map<String, PrelievoBancomat> listaPrelieviBancomat;
    public Map<String, Prelievo> listaPrelievi;
    public Map<String, Deposito> listaDepositi;
    public Map<String, ServizioBancario> listaServiziBancari;


    public ContoCorrente(String iban, String cf) throws FileNotFoundException {
        this.cf=cf;
        this.iban = iban;
        this.saldo = 0;
        this.numeroCarta =generaNumeroCarta();
        this.dataScadenza = LocalDate.now().plusYears(20);
        this.pin = generaPin();
        this.listaPrelieviBancomat = new HashMap<>();
        this.listaPrelievi = new HashMap<>();
        this.listaDepositi = new HashMap<>();
        this.listaServiziBancari = new HashMap<>();
        caricaOperazioniBancarie();
        caricaServiziBancari();
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
        this.listaServiziBancari = new HashMap<>();
        caricaOperazioniBancarie();
        caricaServiziBancari();
    }

    public static ContoCorrente getInstance() throws IOException {
        if (conto == null)
            conto = new ContoCorrente("IT88A5315876888350827758213", "Utente1", 20791, "8083154734643135", "2032-10-12", "4532");
        return conto;
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
            String file = FilePaths.OPERAZIONI_BANCARIE_PATH;
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

    public void caricaServiziBancari()
    {
        try {
            String file = FilePaths.SERVIZI_BANCARI_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String iban = fp.readLine(); iban != null; iban = fp.readLine() )
            {
                if (iban.equals(this.iban))
                {
                    String id = fp.readLine();
                    String tipologia = fp.readLine();
                    float importo = Float.parseFloat(fp.readLine());
                    LocalDate data = LocalDate.parse(fp.readLine());
                    boolean attivo = Boolean.parseBoolean(fp.readLine());
                    LocalDate dataScadenza = LocalDate.parse(fp.readLine());
                    int numeroRate = Integer.parseInt(fp.readLine());
                    float valoreRata = Float.parseFloat(fp.readLine());
                    float interesse = Float.parseFloat(fp.readLine());

                    ServizioBancario servizio = new ServizioBancario(id, iban, importo, data, dataScadenza, attivo, numeroRate, valoreRata, tipologia, interesse);
                    this.listaServiziBancari.put(servizio.getId(), servizio);

                    if(this.listaServiziBancari == null)
                        throw new Exception("Errore caricamento Servizi Bancari");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void creaPrelievoBancomat(int prelievo, int idBancomat)
    {
        PrelievoBancomat prelievoBancomat = new PrelievoBancomat("PrelievoBancomat", prelievo, iban,idBancomat+1);
        this.listaPrelieviBancomat.put(prelievoBancomat.getId(), prelievoBancomat);
        System.out.println("\nPrelievo effettuato correttamente\n");
    }

    public void creaPrelievoFiliale(float importo) throws Exception
    {
        if (importo < this.getSaldo())
        {
            this.setSaldo(this.getSaldo() - importo);

            Prelievo prelievo = new Prelievo("Prelievo", importo, iban);
            this.listaPrelievi.put(prelievo.getId(),prelievo);
            System.out.println("\nPrelievo effettuato correttamente\n");
        }
        else
        {
            System.out.println("\nERRORE: L'IMPORTO INSERITO E' SUPERIORE AL SALDO");
            throw new Exception("ERRORE: L'IMPORTO INSERITO E' SUPERIORE AL SALDO");
        }
    }

    public void creaDeposito(float importo, String nomeM, String cognomeM, String iban) throws Exception
    {
        this.setSaldo(this.getSaldo() + importo);
        Deposito deposito = new Deposito("Deposito", importo, iban, nomeM, cognomeM);
        this.listaDepositi.put(deposito.getId(),deposito);
        if ( this.listaDepositi.get(deposito.getId()) == null)
        {
            System.out.println("\nERRORE: DEPOSITO NON CREATO");
            throw new Exception("ERRORE: DEPOSITO NON CREATO");
        }
        else System.out.println("\nDeposito effettuato correttamente\n");
    }

    public void creaMutuo(float importo, float stipendio, String iban) throws Exception
    {
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

        if(importo < stipendio*8)
        {
            ServizioBancario mutuo = new ServizioBancario(iban, importo, LocalDate.now().plusYears(10), 119, 0,"Mutuo");

            int scelta2 = -1;
            try
            {
                System.out.println("""
            
                                    TASSO FISSO O VARIABILE
            
                                    Inserisci la tua scelta:
                                    1) Tasso Fisso
                                    2) Tasso Variabile
                                    0) Esci
                                    """);

                scelta2 = Integer.parseInt(tastiera.readLine());
                if (scelta2 < 0 || scelta2 > 2) {
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            GestioneInteresse gest = new GestioneInteresse();
            switch (scelta2) {
                case 1: gest.setStrategyInteresse(new StrategyInteresseAlto());
                    mutuo.setInteresse(0.1F);
                    break;
                case 2: gest.setStrategyInteresse(new StrategyInteresseVariabile());
                    mutuo.setInteresse(1F);
                    break;
                default:
                    break;
            }

            mutuo.setValoreRata(gest.calcola(importo, 120));
            this.listaServiziBancari.put(mutuo.getId(), mutuo);
            this.setSaldo(this.getSaldo() + importo - mutuo.getValoreRata());
            System.out.println("\nMutuo creato correttamente");
        }
        else
        {
            System.out.println("\n"+"ERRORE: L'importo inserito è troppo alto in proporzione allo stipendio"+"\n"+"La banca non può fornire all'utente il mutuo richiesto");
            throw new Exception("ERRORE: L'importo inserito è troppo alto in proporzione allo stipendio");
        }
    }


    public void creaPrestito(float importo, float stipendio, String iban) throws Exception
    {
        if(importo < stipendio*5)
        {
            ServizioBancario prestito = new ServizioBancario(iban, importo, LocalDate.now().plusYears(4), 47, 0,"Prestito");
            GestioneInteresse gest = new GestioneInteresse();
            if(importo < stipendio*5/2) {
                gest.setStrategyInteresse(new StrategyInteresseBasso());
                prestito.setInteresse(0.02F);
            }else {
                gest.setStrategyInteresse(new StrategyInteresseMedio());
                prestito.setInteresse(0.05F);
            }
            prestito.setValoreRata(gest.calcola(importo, 48));
            this.listaServiziBancari.put(prestito.getId(), prestito);
            this.setSaldo(this.getSaldo() + importo - prestito.getValoreRata());
            System.out.println("\nPrestito creato correttamente");
        }
        else
        {
            System.out.println("\n"+"ERRORE: L'importo inserito è troppo alto in proporzione allo stipendio"+"\n"+"E' consigliato effettuare un Mutuo");
            throw new Exception("ERRORE: L'importo inserito è troppo alto in proporzione allo stipendio");
        }
    }

    public void pagaRata(String idServizio) throws Exception
    {
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

        if (this.listaServiziBancari.containsKey(idServizio))
        {
            try
            {
                ServizioBancario servizio = this.listaServiziBancari.get(idServizio);
                if (servizio.isAttivo())
                {
                    System.out.println("Servizio trovato...\n");
                    System.out.println("Inserire il numero di rate che si vogliono pagare\n");
                    int nRate = Integer.parseInt(tastiera.readLine());

                    if (nRate <= servizio.getNumeroRate())
                    {
                        float importo = nRate*servizio.getValoreRata();

                        if(this.getSaldo() > importo)
                        {
                            this.setSaldo(this.getSaldo()-importo);
                            servizio.setNumeroRate(servizio.getNumeroRate()-nRate);

                            if (servizio.getNumeroRate()==0)
                                servizio.setAttivo(false);

                            for (int i = 0; i < nRate; i++)
                            {
                                Rata rataAttuale = new Rata(servizio.getId(), LocalDate.now(), servizio.getValoreRata());
                                servizio.listaRate.put(rataAttuale.getId(), rataAttuale);
                            }
                            System.out.println("\nRate pagate correttamente");
                        }

                        else {
                            System.out.println("ERRORE: Non hai abbastanza saldo per effettuare questa operazione\n");
                            throw new Exception("ERRORE: Non hai abbastanza saldo per effettuare questa operazione");
                        }
                    }
                    else {
                        System.out.println("ERRORE: Numero di rate selezionate superiore a quelle rimanenti\n");
                        throw new Exception("ERRORE: Numero di rate selezionate superiore a quelle rimanenti");
                    }
                }
                else {
                    System.out.println("ERRORE: Il servizio selezionato non è attivo\n");
                    throw new Exception("ERRORE: Il servizio selezionato non è attivo");
                }
            } catch (Exception e){e.printStackTrace();}
        } else {
            System.out.println("ERRORE: Servizio non trovato...\n");
            throw new Exception("ERRORE: Servizio non trovato...");
        }
    }
}