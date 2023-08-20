package org.unict.domain;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

//Observer Pattern
public class BancaISA implements Observer{
    private static BancaISA bancaIsa;
    private Cliente clienteCorrente;
    private final BufferedReader tastiera;
    private Bancomat bancomat;
    public LinkedList<Bancomat> listaBancomat;
    public LinkedList<String> listaNotifiche;
    private Map<String, Cliente> listaClienti;
    public Map<String, ContoCorrente> listaCc;
    public Map<Integer, Banconota> listaBanconote;


    /** ----------------------- BancaISA -------------------------- */
    private BancaISA() throws IOException {
        this.tastiera = new BufferedReader(new InputStreamReader(System.in));
        this.listaBancomat = new LinkedList<>();
        this.listaNotifiche = new LinkedList<>();
        this.listaClienti = new HashMap<>();
        this.listaCc = new HashMap<>();
        this.listaBanconote = new HashMap<>();
        caricaClienti();
        caricaListaBancomat();
        aggiornaFileBanconote();
        caricaNotifiche();
    }

    /** ------------------- getInstance ------------------------ */
    public static BancaISA getInstance() throws IOException {
        if (bancaIsa == null)
            bancaIsa = new BancaISA();
        return bancaIsa;
    }

    /** ----------------- caricaClienti ---------------------- */
    public void caricaClienti()
    {
        try {
            String file = FilePaths.ELENCO_CLIENTI_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String cf = fp.readLine(); cf != null; cf = fp.readLine())
            {
                String nome = fp.readLine();
                String cognome = fp.readLine();
                String dataNascita = fp.readLine();
                String email = fp.readLine();
                String telefono = fp.readLine();

                Cliente c = new Cliente(cf, nome, cognome, dataNascita, email, telefono);
                this.listaClienti.put(cf, c);
                this.listaCc.putAll(c.getListaCc());

                if (this.listaClienti == null)
                    throw new Exception("Errore caricamento clienti");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** ------------------- verificaCredenziali ------------------------- */
    public boolean verificaCredenziali(String cf) throws Exception {
        if (this.listaClienti.containsKey(cf)) {
            try
            {
                clienteCorrente = this.listaClienti.get(cf);
                System.out.println("Cliente esistente, creazione conto corrente in corso...\n");
                // throw new Exception("Cliente esistente, creazione conto corrente in corso...\n");
                clienteCorrente.creaContoCorrente();
                return false;
            } catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        } else {
            /* ------------------- UC12 CreaCliente ------------------- */

            int scelta2 = -1;
            do
            {  try
                {
                    System.out.println("""

                        VUOI CREARE UN NUOVO CLIENTE?

                        Inserisci la tua scelta:
                        0) Si
                        1) No
                        """);

                    scelta2 = Integer.parseInt(tastiera.readLine());
                    if (scelta2 < 0 || scelta2 > 1) {
                        System.out.println("Scelta non valida");
                        throw new Exception("Scelta non valida");
                    }
                } catch(Exception ignored){}
                switch (scelta2) {
                    case 1: inserisciCredenziali(cf);
                        break;
                    default:
                        break;
                }
            }while(scelta2 != 0);

            return true;
        }
    }

    /** ---------------- inserisciCredenziali --------------------- */
    public void inserisciCredenziali(String cf) throws Exception {
        System.out.println("inserisci nome:\n");
        String nome = this.tastiera.readLine();
        System.out.println("inserisci cognome:\n");
        String cognome = this.tastiera.readLine();
        System.out.println("inserisci data di nascita:\n");
        String dataNascita = this.tastiera.readLine();
        System.out.println("inserisci email:\n");
        String email = this.tastiera.readLine();
        System.out.println("inserisci telefono:\n");
        String telefono = this.tastiera.readLine();

        clienteCorrente = new Cliente(cf, nome, cognome, dataNascita, email, telefono);
        this.listaClienti.put(cf, clienteCorrente);
        FileWriter file = new FileWriter(FilePaths.ELENCO_CLIENTI_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);
        this.listaClienti.forEach((key, value) -> printout.println
                (key
                        + "\n" + value.getNome()
                        + "\n" + value.getCognome()
                        + "\n" + value.getDataNascita()
                        + "\n" + value.getEmail()
                        + "\n" + value.getTelefono()));

        printout.flush();
        printout.close();

        clienteCorrente.creaContoCorrente();
    }

    /** --------------------- Menu Dipendente Normale ----------------------- */
    public void menuDipendenteN() throws Exception {
        System.out.println("sono nel menu N");

        int scelta = -1;
        do {
            try
            {
                System.out.println("""

                        ****MENU DIPENDENTE NORMALE****

                        Inserisci la tua scelta:
                        1) Crea conto corrente
                        2) Deposito
                        3) Prelievo
                        4) Mutuo
                        5) Prestito
                        6) Visualizza lista delle operazioni bancarie
                        7) Visualizza lista dei servizi bancari
                        8) Paga rata
                        0) Esci""");

                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 8) {
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            switch (scelta) {
                case 1:
                    /* ------- UC1 CreaContoCorrente ------- */
                    System.out.println("\n\n ------------- CREAZIONE CONTO CORRENTE -------------\n");
                    System.out.println("Inserisci il Codice Fiscale");
                    verificaCredenziali(tastiera.readLine()); //verifichiamo le credenziali del cliente
                    break;

                case 2:
                    /* ------- UC5 Deposito ------- */
                    System.out.println("\n\n ------------- DEPOSITO -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN = tastiera.readLine();
                    System.out.println("Inserisci il Codice Fiscale del Beneficiario");
                    String codiceFiscale = tastiera.readLine();
                    verificaEsistenzaCc(IBAN, codiceFiscale, "Deposito");
                    break;

                case 3:
                    /* ------- UC3 Prelievo ------- */
                    System.out.println("\n\n ------------- PRELIEVO -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN2 = tastiera.readLine();
                    System.out.println("Inserisci il Codice Fiscale");
                    String codiceFiscale2 = tastiera.readLine();
                    verificaEsistenzaCc(IBAN2, codiceFiscale2, "Prelievo");
                    break;

                case 4:
                    /* ------- UC6 Mutuo ------- */
                    System.out.println("\n\n ------------- MUTUO -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN3 = tastiera.readLine();
                    System.out.println("Inserisci il Codice Fiscale");
                    String codiceFiscale3 = tastiera.readLine();
                    verificaEsistenzaCc(IBAN3, codiceFiscale3, "Mutuo");
                    break;

                case 5:
                    /* ------- UC6 Prestito ------- */
                    System.out.println("\n\n ------------- PRESTITO -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN4 = tastiera.readLine();
                    System.out.println("Inserisci il Codice Fiscale");
                    String codiceFiscale4 = tastiera.readLine();
                    verificaEsistenzaCc(IBAN4, codiceFiscale4, "Prestito");
                    break;

                case 6:
                    /* ------- UC11 Visualizza Operazioni Bancarie ------- */
                    System.out.println("\n\n ------------- LISTA TRANSAZIONI BANCARIE -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN5= tastiera.readLine();
                    stampaOperazioniBancarieSuConsole(IBAN5);
                    break;

                case 7:
                    /* ------- UC11 Visualizza Servizi Bancari ------- */
                    System.out.println("\n\n ------------- LISTA SERVIZI BANCARI ------------- \n");
                    System.out.println("Inserisci IBAN");
                    String IBAN6= tastiera.readLine();
                    stampaServiziBancariSuConsole(IBAN6);
                    break;

                case 8:
                    /* ------- UC6.2 Paga Rata ------- */
                    System.out.println("\n\n ------------- PAGA RATA -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN7= tastiera.readLine();
                    pagaRata(IBAN7);
                    break;

                default:
                    break;
            }
        } while (scelta != 0);
    }

    public void verificaEsistenzaCc(String iban, String codiceFiscale, String operazione) {
        try
        {
            if (this.listaCc.get(iban) != null) {
                verificaOperazione(iban, codiceFiscale, operazione);
            } else
            {
                System.out.println("\nERRORE: IBAN NON ESISTENTE");
                throw new Exception("ERRORE: IBAN NON ESISTENTE");
            }
        }
        catch(Exception ignored){}
    }

    public void verificaOperazione(String iban, String codiceFiscale, String operazione)
    {
        try
        {
            switch (operazione)
            {
                case "Prelievo": if(this.listaClienti.get(this.listaCc.get(iban).getCf()).getCf().equals(codiceFiscale))
                                    {
                                        System.out.println("*Dati Convalidati\n\n"+"Inserisci Importo da Prelevare");
                                        float importo = Float.parseFloat(tastiera.readLine());

                                        this.listaCc.get(iban).setSaldo(this.listaCc.get(iban).getSaldo() - importo);
                                        stampaCcSuFile();

                                        Prelievo prelievo = new Prelievo("Prelievo", importo, iban);
                                        this.listaCc.get(iban).listaPrelievi.put(prelievo.getId(),prelievo);
                                        stampaOperazioniBancarieSuFile();
                                    }
                                    else
                                    {
                                        System.out.println("\nERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                        throw new Exception("ERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                    }
                                break;

                case "Deposito": if(this.listaClienti.get(this.listaCc.get(iban).getCf()).getCf().equals(codiceFiscale))
                                    {
                                        System.out.println("*Dati Convalidati\n\n"+"Inserisci Importo da Depositare");
                                        float importo = Float.parseFloat(tastiera.readLine());
                                        System.out.println("Inserisci Nome Mittente");
                                        String nomeM = tastiera.readLine();
                                        System.out.println("Inserisci Cognome Mittente");
                                        String cognomeM = tastiera.readLine();

                                        this.listaCc.get(iban).setSaldo(this.listaCc.get(iban).getSaldo() + importo);
                                        stampaCcSuFile();

                                        Deposito deposito = new Deposito("Deposito", importo, iban, nomeM, cognomeM);
                                        this.listaCc.get(iban).listaDepositi.put(deposito.getId(),deposito);
                                        stampaOperazioniBancarieSuFile();
                                    }
                                    else
                                    {
                                        System.out.println("\nERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                        throw new Exception("ERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                    }
                                break;
                case "Mutuo": if(this.listaClienti.get(this.listaCc.get(iban).getCf()).getCf().equals(codiceFiscale))
                                    {
                                        System.out.println("*Dati Convalidati\n\n"+"Inserisci il Mutuo richiesto");
                                        float importo = Float.parseFloat(tastiera.readLine());
                                        System.out.println("\n"+"Inserisci lo stipendio attuale del cliente");
                                        float stipendio = Float.parseFloat(tastiera.readLine());

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
                                                    0) Esci""");

                                                scelta2 = Integer.parseInt(tastiera.readLine());
                                                if (scelta2 < 0 || scelta2 > 2) {
                                                    System.out.println("Scelta non valida");
                                                    throw new Exception("Scelta non valida");
                                                }
                                            } catch(Exception ignored){}

                                            switch (scelta2) {
                                                case 1: mutuo.setStrategyInteresse(new StrategyInteresseAlto());
                                                        mutuo.setInteresse(0.1F);
                                                    break;
                                                case 2: mutuo.setStrategyInteresse(new StrategyInteresseVariabile());
                                                        mutuo.setInteresse(1F);
                                                    break;
                                                default:
                                                    break;
                                            }

                                            mutuo.setValoreRata(mutuo.calcolaInteresse());
                                            this.listaCc.get(iban).listaServiziBancari.put(mutuo.getId(), mutuo);
                                            this.listaCc.get(iban).setSaldo(this.listaCc.get(iban).getSaldo()-mutuo.getValoreRata());
                                            stampaCcSuFile();
                                            stampaServiziBancariSuFile();
                                            System.out.println("\nMutuo creato correttamente");
                                        }
                                        else
                                        {
                                            System.out.println("\n"+"L'importo inserito è troppo alto in proporzione allo stipendio"+"\n"+"La banca non può fornire all'utente il mutuo richiesto");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("\nERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                        throw new Exception("ERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                    }
                                    break;
                case "Prestito": if(this.listaClienti.get(this.listaCc.get(iban).getCf()).getCf().equals(codiceFiscale))
                                    {
                                        System.out.println("*Dati Convalidati\n\n"+"Inserisci il Prestito richiesto");
                                        float importo = Float.parseFloat(tastiera.readLine());
                                        System.out.println("\n"+"Inserisci lo stipendio attuale del cliente");
                                        float stipendio = Float.parseFloat(tastiera.readLine());

                                        if(importo < stipendio*5)
                                        {
                                            ServizioBancario prestito = new ServizioBancario(iban, importo, LocalDate.now().plusYears(4), 47, 0,"Prestito");
                                            if(importo < stipendio*5/2) {
                                                prestito.setStrategyInteresse(new StrategyInteresseBasso());
                                                prestito.setInteresse(0.02F);
                                            }else {
                                                prestito.setStrategyInteresse(new StrategyInteresseMedio());
                                                prestito.setInteresse(0.05F);
                                            }
                                            prestito.setValoreRata(prestito.calcolaInteresse());
                                            this.listaCc.get(iban).listaServiziBancari.put(prestito.getId(), prestito);
                                            this.listaCc.get(iban).setSaldo(this.listaCc.get(iban).getSaldo()-prestito.getValoreRata());
                                            stampaCcSuFile();
                                            stampaServiziBancariSuFile();
                                            System.out.println("\nPrestito creato correttamente");
                                        }
                                        else
                                        {
                                            System.out.println("\n"+"L'importo inserito è troppo alto in proporzione allo stipendio"+"\n"+"E' consigliato effettuare un Mutuo");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("\nERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                        throw new Exception("ERRORE: CODICE FISCALE NON APPARTENENTE ALL'IBAN");
                                    }
                                    break;
                default: break;
            }
        }
        catch(Exception ignored){}
    }

    /** --------------------------------------- Menu Dipendente Tecnico --------------------------------------- **/
    public void menuDipendenteT() throws IOException {
        int notifiche = 0;
        int scelta = -1;

        //Visualizza notifiche o esci
        //Dentro visualizza notifiche -> Aggiungi banconote o esci
        do {
            try
            {
                /* --------------------------------------- Conta Notifiche ----------------------------------- */
                notifiche = 0;
                String file = FilePaths.NOTIFICHE_DIPENDENTE_T_PATH;
                BufferedReader fp = new BufferedReader(new FileReader(file));

                for (String s = fp.readLine(); s != null; s = fp.readLine())
                {
                    notifiche ++;
                }
            } catch(Exception e){ e.printStackTrace(); }

            try
            {
                System.out.println("\n****MENU DIPENDENTE TECNICO****\n" +
                        "\n Hai " + notifiche + " notifiche\n" +
                        "\nInserisci la tua scelta:" +
                        "\n1) Visualizza le notifiche" +
                        "\n0) Esci");
                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 1) { //Aggiornare man mano che implementiamo i casi d'uso
                    System.out.println("\nERRORE: Scelta non valida\n");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            switch (scelta)
            {
                case 1:
                    /* ------------------------ UC10 Gestione Banconote Bancomat ------------------------ **/
                    leggiNotifiche();
                    break;

                default:
                    break;
            }
        } while (scelta != 0);
    }

    private void leggiNotifiche() throws IOException {
        int codiceBancomat =0;
        int codiceBanconota =0;
        int scelta = -1;

        try
        {
            /* --------------------------------------- Stampa Notifiche ----------------------------------- **/

            String file = FilePaths.NOTIFICHE_DIPENDENTE_T_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {
                System.out.println(s);
            }
        } catch(Exception e){ e.printStackTrace(); }

        do {
            try
            {
                System.out.println("""
                        ****MENU DIPENDENTE TECNICO****

                        Inserisci la tua scelta:
                        1) Inserisci pezzi nel Bancomat
                        0) Esci
                        """);

                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 1) { //Aggiornare man mano che implementiamo i casi d'uso
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            switch (scelta)
            {
                case 1:
                    /* ------------------------ Aggiorna Pezzi ------------------------ **/
                    try
                    {
                        System.out.println("\n Inserisci codice bancomat:");
                        codiceBancomat = Integer.parseInt(tastiera.readLine());
                        if(codiceBancomat<=listaBancomat.size() && codiceBancomat > 0)
                        {
                            System.out.println("\n Inserisci codice banconota:");
                            codiceBanconota = Integer.parseInt(tastiera.readLine());
                            if(codiceBanconota==5 ||codiceBanconota==10 || codiceBanconota==20 ||
                                codiceBanconota==50 ||codiceBanconota==100 ||codiceBanconota==200)
                            {
                                aggiornaPezziBanconota(codiceBancomat, codiceBanconota);
                                break;
                            }
                        }
                        else
                        {
                            System.out.println("\nErrore: Codice Bancomat o Codice Banconota Non Valido\n");
                            throw new Exception("Errore: Codice Bancomat o Codice Banconota Non Valido");
                        }
                    }
                    catch(Exception ignored) {}

                default:
                    break;
            }
        } while (scelta != 0);
    }

    private void aggiornaPezziBanconota(int codiceBancomat, int codiceBanconota) throws IOException {

        /* Stiamo mettendo che riempie di 50 banconote per semplicità logica*/

        Bancomat ban = this.listaBancomat.get(codiceBancomat-1);
        int numRicarica = (1000 - (this.listaBanconote.get((codiceBancomat+codiceBanconota)).getNumPezzi() * codiceBanconota))/codiceBanconota;
        Banconota b = new Banconota( ban.getCodice(), codiceBanconota, numRicarica );
        ban.listaBanconote.replace(codiceBanconota, b);
        aggiornaFileBanconote();
        aggiornaNotifiche(ban.getCodice(), codiceBanconota);

    }

   private void aggiornaNotifiche(int codiceBancomat, int codiceBaconota) throws IOException {
       String[] splitStr;

       for(String s: this.listaNotifiche)
       {
           splitStr = s.split("\\s+");

           if(splitStr[0].equals(String.valueOf(codiceBancomat)) && splitStr[1].equals(String.valueOf(codiceBaconota)))
               this.listaNotifiche.remove(s);
       }

       notificaBanconote();
   }


    /** --------------- Menu Bancomat ----------------------- */
    public void menuBancomat()
    {
        int idBancomat = 0;
        System.out.println("Sono Nel Menu Cliente del Bancomat " + idBancomat + "\n");
        bancomat = this.listaBancomat.get(idBancomat);
        bancomat.caricaListaBanconote();


        try
        {
            /* ------------- UC4 Prelievo Bancomat ------------- */
            System.out.println("****INSERIMENTO CREDENZIALI****\n");
            System.out.println("Inserisci iban\n");
            //String iban = tastiera.readLine();
            String iban = "IT88A5315876888350827758213";
            System.out.println("Inserisci pin\n");
            //String pin = tastiera.readLine();
            String pin = "4532";

            if (this.listaCc.containsKey(iban) && Objects.equals(this.listaCc.get(iban).getPin(), pin))
            {
                System.out.println("Saldo disponibile: €" + this.listaCc.get(iban).getSaldo());

                try
                {
                    int prelievo = inserisciImporto();
                    if (prelievo == 0)
                    {
                        return;
                    }
                    //Verifichiamo se il nostro saldo ci basta per poter effetturare il prelievo
                    else if(this.listaCc.get(iban).getSaldo() >= prelievo)
                    {
                        bancomat.calcolaPrelievo(prelievo);
                        aggiornaFileBanconote();
                        this.listaCc.get(iban).setSaldo ((int) (this.listaCc.get(iban).getSaldo()-prelievo));
                        stampaCcSuFile();

                        /* ------------------------------------------------------------- Aggiunta operazioni bancarie ------------------------------------------------------------- */
                        PrelievoBancomat prelievoBancomat = new PrelievoBancomat("PrelievoBancomat", prelievo, iban,idBancomat+1);
                        this.listaCc.get(iban).listaPrelieviBancomat.put(prelievoBancomat.getId(), prelievoBancomat);
                        stampaOperazioniBancarieSuFile();
                    }
                    else
                    {
                        System.out.println("\nERRORE: Non hai abbastanza soldi nel conto da prelevare\n");
                        throw new Exception("Non hai abbastanza soldi nel conto da prelevare");
                    }
                }
                catch(Exception ignored){}
            }
            else
            {
                System.out.println("IBAN NON TROVATO O CREDENZIALI ERRATE\n");
                throw new Exception("IBAN NON TROVATO O CREDENZIALI ERRATE");
            }
        }
        catch(Exception ignored){}
    }

    private void stampaOperazioniBancarieSuFile() throws IOException
    {
        FileWriter file = new FileWriter(FilePaths.OPERAZIONI_BANCARIE_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        this.listaCc.forEach((key, value) -> value.listaPrelieviBancomat.forEach((key2, value2) ->
        {
            printout.println (value2.getIban()
                    + "\n" + value2.getNomeOP()
                    + "\n" + key2
                    + "\n" + value2.getCodiceBancomat()
                    + "\n" + value2.getImporto()
                    + "\n" + value2.getData()
            );
        }));

        this.listaCc.forEach((key, value) -> value.listaPrelievi.forEach((key2, value2) ->
        {
            printout.println (value2.getIban()
                    + "\n" + value2.getNomeOP()
                    + "\n" + key2
                    + "\n" + value2.getImporto()
                    + "\n" + value2.getData()
            );
        }));

        this.listaCc.forEach((key, value) -> value.listaDepositi.forEach((key2, value2) ->
        {
            printout.println (value2.getIban()
                    + "\n" + value2.getNomeOP()
                    + "\n" + key2
                    + "\n" + value2.getImporto()
                    + "\n" + value2.getNomeMittente()
                    + "\n" + value2.getCognomeMittente()
                    + "\n" + value2.getData()
            );
        }));

        printout.flush();
        printout.close();

    }

    private void stampaOperazioniBancarieSuConsole(String iban)
    {

        System.out.println("\n----- LISTA PRELIEVI BANCOMAT -----\n");
        this.listaCc.get(iban).listaPrelieviBancomat.forEach((key, value)->
            System.out.println
                (
                    "Data: " + value.getData() + "\n" +
                    "ID: " + key + "\n" +
                    "Codice Bancomat: " + value.getCodiceBancomat() + "\n" +
                    "Importo: " + value.getImporto() + "\n"
                )
        );

        System.out.println("\n----- LISTA PRELIEVI -----\n");
        this.listaCc.get(iban).listaPrelievi.forEach((key, value)->
            System.out.println
                (
                    "Data: " + value.getData() + "\n" +
                    "ID: " + key + "\n" +
                    "Importo: " + value.getImporto() + "\n"
                )
        );

        System.out.println("\n----- LISTA DEPOSITI -----\n");
        this.listaCc.get(iban).listaDepositi.forEach((key, value)->
            System.out.println
                (
                    "Data: " + value.getData() + "\n" +
                    "ID: " + key + "\n" +
                    "Importo: " + value.getImporto() + "\n" +
                    "Nome Mittente: " + value.getNomeMittente() + "\n" +
                    "Cognome Mittente: " + value.getCognomeMittente() + "\n"
                )
        );
    }

    private void stampaServiziBancariSuFile() throws IOException
    {
        FileWriter file = new FileWriter(FilePaths.SERVIZI_BANCARI_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        this.listaCc.forEach((key, value) -> value.listaServiziBancari.forEach((key2, value2) ->
        {
            printout.println (value2.getIban()
                    + "\n" + key2   //id
                    + "\n" + value2.getTipologia()
                    + "\n" + value2.getImporto()
                    + "\n" + value2.getData()
                    + "\n" + value2.isAttivo()
                    + "\n" + value2.getDataScadenza()
                    + "\n" + value2.getNumeroRate()
                    + "\n" + value2.getValoreRata()
                    + "\n" + value2.getInteresse()
            );
        }));

        printout.flush();
        printout.close();
    }

    private void stampaServiziBancariSuConsole(String iban)
    {
        System.out.println("----------------------- LISTA SERVIZI BANCARI -----------------------");
        this.listaCc.get(iban).listaServiziBancari.forEach((key, value)->
            System.out.println
                (
                    "IBAN: " + value.getIban() + "\n" +
                    "ID: " + key + "\n" +
                    "Tipologia: " + value.getTipologia() + "\n" +
                    "Importo: " + value.getImporto() + "\n" +
                    "Data: " + value.getData() + "\n" +
                    "Attivo: " + value.isAttivo() + "\n" +
                    "Data Scadenza: " + value.getDataScadenza() + "\n" +
                    "Numero Rate: " + value.getNumeroRate() + "\n" +
                    "Valore Rata: " + value.getValoreRata() + "\n" +
                    "Interesse: " + value.getInteresse() + "\n\n"
                )
        );
    }

    private void pagaRata(String iban) throws IOException {
        stampaServiziBancariSuConsole(iban);
        System.out.println("Inserire l'id del servizio di cui si vuole pagare la rata\n");
        String id = tastiera.readLine();

        if (this.listaCc.get(iban).listaServiziBancari.containsKey(id)) {
            try
            {
                ServizioBancario servizio = this.listaCc.get(iban).listaServiziBancari.get(id);
                if (servizio.isAttivo())
                {
                    System.out.println("Servizio trovato...\n");
                    System.out.println("Inserire il numero di rate che si vogliono pagare\n");
                    int nRate = Integer.parseInt(tastiera.readLine());

                    if (nRate <= servizio.getNumeroRate())
                    {
                        float importo = nRate*servizio.getValoreRata();

                        if(this.listaCc.get(iban).getSaldo() > importo)
                        {
                            this.listaCc.get(iban).setSaldo(this.listaCc.get(iban).getSaldo()-importo);
                            servizio.setNumeroRate(servizio.getNumeroRate()-nRate);

                            if (servizio.getNumeroRate()==0)
                                servizio.setAttivo(false);

                            for (int i = 0; i < nRate; i++)
                            {
                                Rata rataAttuale = new Rata(servizio.getId(), LocalDate.now(), servizio.getValoreRata());
                                servizio.listaRate.put(rataAttuale.getId(), rataAttuale);
                            }

                            stampaCcSuFile();
                            stampaServiziBancariSuFile();
                            stampaRateSuFile();
                            System.out.println("\nRate pagate correttamente");
                        }

                        else
                            System.out.println("Non hai abbastanza saldo per effettuare questa operazione\n");
                    }
                    else
                        System.out.println("Numero di rate selezionate superiore a quelle rimanenti\n");
                }
                else
                    System.out.println("Il servizio selezionato non è attivo\n");

            } catch (Exception e){e.printStackTrace();}
        } else {
            System.out.println("Servizio non trovato...\n");
        }
    }

    private void stampaRateSuFile() throws IOException
    {
        FileWriter file = new FileWriter(FilePaths.ELENCO_RATE_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        this.listaCc.forEach((key, value) -> value.listaServiziBancari.forEach((key2, value2) -> value2.listaRate.forEach((key3, value3) ->
                printout.println (key2 //id del servizio bancario
                        + "\n" + key3  //id della rata
                        + "\n" + value3.getData()
                        + "\n" + value3.getImporto()
                ))));

        printout.flush();
        printout.close();
    }

    private void stampaCcSuFile() throws IOException
    {
        FileWriter file = new FileWriter(FilePaths.ELENCO_CC_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        this.listaCc.forEach((key, value) -> printout.println
                (value.getCf()
                        + "\n" + key
                        + "\n" + value.getSaldo()
                        + "\n" + value.getNumeroCarta()
                        + "\n" + value.getDataScadenza()
                        + "\n" + value.getPin()
                ));

        printout.flush();
        printout.close();
    }

    private void aggiornaFileBanconote() throws IOException
    {

        FileWriter file = new FileWriter(FilePaths.ELENCO_BANCONOTE_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        for (Bancomat item : this.listaBancomat)
            item.listaBanconote.forEach((key, value) -> this.listaBanconote.put((value.getCodiceBancomat() + value.getCodice()), value));

        this.listaBanconote.forEach((key, value) -> printout.println
                (value.getCodiceBancomat()
                        + "\n" + value.getCodice()
                        + "\n" + value.getNumPezzi()
                ));
        printout.flush();
        printout.close();
    }

    public int inserisciImporto()throws IOException
    {
        System.out.println("""
                Selezione importo:
                0) Esci
                1) €100\s
                2) €200\s
                3) €300\s
                5) €500\s
                7) €700\s
                10) €1000\s
                """);
        int scelta = Integer.parseInt(tastiera.readLine());

        if (scelta==0)
        {
            return 0;
        }else if(scelta==1 || scelta==2 || scelta==3 || scelta==5 || scelta==7 || scelta==10)
        {
            return scelta*100;
        }else {
            System.out.println("valore inserito non valido\n");
            bancaIsa.inserisciImporto();
        }
        return 0;
    }

    public void caricaListaBancomat()
    {
        try {
            String file = FilePaths.ELENCO_BANCOMAT_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            int codiceBancomat = 0;
            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {
                codiceBancomat = Integer.parseInt(s);
                String posizione = fp.readLine();

                Bancomat b = new Bancomat(codiceBancomat, posizione);
                this.listaBancomat.add(b);

                b.addObserver(this);

                if (this.listaBancomat == null)
                {
                    throw new Exception("Errore caricamento Bancomat");
                }

            }
        } catch (Exception e) {e.printStackTrace();}
    }

    private void caricaNotifiche()
    {
        try {
            String file = FilePaths.NOTIFICHE_DIPENDENTE_T_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));

            String[] splitStr;
            for (String notifica = fp.readLine(); notifica != null; notifica = fp.readLine())
            {
                splitStr = notifica.split("\\s+");
                notifica = ( splitStr[2] + " " + splitStr[7] + " " + splitStr[11]);
                this.listaNotifiche.add(notifica);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }


    //------------------------------------------------  Observer Pattern ------------------------------------------------------
    @Override
    public void update(Observable obs, Object arg) {
        Bancomat bancomat = (Bancomat) obs;

        verificaEAggiungiNotifica(bancomat, 5, 100);
        verificaEAggiungiNotifica(bancomat, 10, 50);
        verificaEAggiungiNotifica(bancomat, 20, 25);
        verificaEAggiungiNotifica(bancomat, 50, 10);
        verificaEAggiungiNotifica(bancomat, 100, 5);
        verificaEAggiungiNotifica(bancomat, 200, 2);

        //Riscriviamo il file con tutte le notifiche
        try {
            notificaBanconote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void verificaEAggiungiNotifica(Bancomat bancomat, int taglio, int soglia) {
        int numPezzi = bancomat.getListaBanconote().get(taglio).getNumPezzi();
        String a = String.valueOf(bancomat.getCodice() + " " + taglio + " " + numPezzi);

        if (numPezzi < soglia) {
            for (int i = 0; i < this.listaNotifiche.size(); i++) {
                String[] splitStr = this.listaNotifiche.get(i).split("\\s+");
                if (splitStr[0].equals(String.valueOf(bancomat.getCodice())) && splitStr[1].equals(String.valueOf(taglio))) {
                    this.listaNotifiche.remove(this.listaNotifiche.get(i));
                    System.out.println("Ho Trovato un doppione " + taglio);
                }
            }
            this.listaNotifiche.add(a);
        }
    }

    public void notificaBanconote() throws IOException
    {
        FileWriter file = new FileWriter(FilePaths.NOTIFICHE_DIPENDENTE_T_PATH);
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        /* Per ogni elemento della lista fa una stampa  */
        String[] splitStr;

        for(String s: this.listaNotifiche)
        {
            splitStr = s.split("\\s+");
            printout.println ("Nel bancomat "+ splitStr[0] +" le banconote da € " + splitStr[1] + " sono ridotte a " + splitStr[2] + " pezzi");
        }

        printout.flush();
        printout.close();
    }
}
