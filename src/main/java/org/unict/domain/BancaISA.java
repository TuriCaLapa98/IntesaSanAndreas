package org.unict.domain;

import java.io.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

//Observer Pattern
public class BancaISA implements Observer{
    private static BancaISA bancaIsa;
    private Cliente clienteCorrente;
    private Map<String, Cliente> listaClienti;
    public Map<String, ContoCorrente> listaCc;
    private final BufferedReader tastiera;
    private Bancomat bancomat;
    public LinkedList<Bancomat> listaBancomat;
    public LinkedList<String> listaNotifiche;
    public Map<Integer, Banconota> listaBanconote;


    /** ----------------------- BancaISA -------------------------- */
    private BancaISA() throws IOException {
        this.listaClienti = new HashMap<>();
        this.listaCc = new HashMap<>();
        this.listaBanconote = new HashMap<>();
        this.listaBancomat = new LinkedList<>();
        this.tastiera = new BufferedReader(new InputStreamReader(System.in));
        this.listaNotifiche = new LinkedList<>();
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
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoClienti.txt";
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
            /** ------------------- UC12 CreaCliente ------------------- */
            //Chiedere da console se si vuole creare il nuovo cliente

            System.out.println("Cliente non esistente, creazione conto corrente in corso...\n");
            inserisciCredenziali(cf);
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
        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoClienti.txt");
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
                System.out.println("\n****MENU DIPENDENTE NORMALE****\n" +
                        "\nInserisci la tua scelta:" +
                        "\n1) Crea conto corrente" +
                        "\n2) Deposito" +
                        "\n3) Prelievo" +
                        "\n4) Mutuo" +
                        "\n5) Prestito" +
                        "\n6) Visualizza cronologia delle operazioni bancarie" +
                        "\n0) Esci");
                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 6) { //Aggiornare man mano che implementiamo i casi d'uso
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            switch (scelta) {
                case 1:
                    /* ------- UC1 CreaContoCorrente ------- */
                    System.out.println("\n\n ------------- CREAZIONE CONTO CORRENTE -------------\n");
                    System.out.println("Inserisci codice fiscale");
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
                    System.out.println("\n\n ------------- Prelievo -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN2 = tastiera.readLine();
                    System.out.println("Inserisci il Codice Fiscale del Beneficiario");
                    String codiceFiscale2 = tastiera.readLine();
                    verificaEsistenzaCc(IBAN2, codiceFiscale2, "Prelievo");
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    /* ------- UC11 Visualizza Operazioni Bancarie ------- */
                    System.out.println("\n\n ------------- LISTA TRANSAZIONI BANCARIE -------------\n");
                    System.out.println("Inserisci IBAN");
                    String IBAN3= tastiera.readLine();
                    stampaOperazioniBancarieSuConsole(IBAN3);
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
                verificaBeneficiario(iban, codiceFiscale, operazione);
            } else
            {
                System.out.println("\nERRORE: IBAN NON ESISTENTE");
                throw new Exception("ERRORE: IBAN NON ESISTENTE");
            }
        }
        catch(Exception ignored){}
    }

    public void verificaBeneficiario(String iban, String codiceFiscale, String operazione)
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
                                        StampaCcSuFile();

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
                                        StampaCcSuFile();

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
                /** --------------------------------------- Conta Notifiche ----------------------------------- **/
                notifiche = 0;
                String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\notificheDipendenteT.txt";
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
                    /** ------------------------ UC10 Gestione Banconote Bancomat ------------------------ **/
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
            /** --------------------------------------- Stampa Notifiche ----------------------------------- **/

            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\notificheDipendenteT.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String s = fp.readLine(); s != null; s = fp.readLine())
            {
                System.out.println(s);
            }
        } catch(Exception e){ e.printStackTrace(); }

        do {
            try
            {
                System.out.println("****MENU DIPENDENTE TECNICO****\n" +
                        "\nInserisci la tua scelta:" +
                        "\n1) Inserisci pezzi nel Bancomat" +
                        "\n0) Esci");
                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 1) { //Aggiornare man mano che implementiamo i casi d'uso
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception ignored){}

            switch (scelta)
            {
                case 1:
                    /** ------------------------ Aggiorna Pezzi ------------------------ **/
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

        /** Stiamo mettendo che riempie di 50 banconote per semplicità logica*/

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
    public void menuCliente()
    {
        int idBancomat = 0;
        System.out.println("Sono Nel Menu Cliente del Bancomat " + idBancomat + "\n");
        bancomat = this.listaBancomat.get(idBancomat);
        bancomat.caricaListaBanconote();


        try
        {
            /** ------------- UC4 Prelievo Bancomat ------------- */
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
                        StampaCcSuFile();

                        /** ------------------------------------------------------------- Aggiunta operazioni bancarie ------------------------------------------------------------- **/
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
        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\operazioniBancarie.txt");
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

    private void stampaOperazioniBancarieSuConsole(String IBAN)
    {

        System.out.println("\n----- LISTA PRELIEVI BANCOMAT -----\n");
        this.listaCc.get(IBAN).listaPrelieviBancomat.forEach((key, value)->
            System.out.println
                (
                    "Data: " + value.getData() + "\n" +
                    "ID: " + key + "\n" +
                    "Codice Bancomat: " + value.getCodiceBancomat() + "\n" +
                    "Importo: " + value.getImporto() + "\n"
                )
        );

        System.out.println("\n----- LISTA PRELIEVI -----\n");
        this.listaCc.get(IBAN).listaPrelievi.forEach((key, value)->
            System.out.println
                (
                    "Data: " + value.getData() + "\n" +
                    "ID: " + key + "\n" +
                    "Importo: " + value.getImporto() + "\n"
                )
        );

        System.out.println("\n----- LISTA DEPOSITI -----\n");
        this.listaCc.get(IBAN).listaDepositi.forEach((key, value)->
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

    private void StampaCcSuFile() throws IOException
    {
        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoCc.txt");
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

        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoBanconote.txt");
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        for(int i=0; i< this.listaBancomat.size();i++ )
            this.listaBancomat.get(i).listaBanconote.forEach((key,value) -> this.listaBanconote.put((value.getCodiceBancomat()+value.getCodice()),value));

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
        System.out.println("Selezione importo:\n" +
                "0) Esci\n" +
                "1) €100 \n" +
                "2) €200 \n" +
                "3) €300 \n" +
                "5) €500 \n" +
                "7) €700 \n" +
                "10) €1000 \n");
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
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoBancomat.txt";
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
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\notificheDipendenteT.txt";
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
    //Observer Pattern
    @Override
    public void update(Observable obs, Object arg)
    {
        try {
            String a = "";
            String[] splitStr;


            if (((Bancomat) obs).getListaBanconote().get(5).getNumPezzi() < 100)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 5 + " " + ((Bancomat) obs).getListaBanconote().get(5).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(5)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 5");
                    }
                }
                this.listaNotifiche.add(a);

            }
            if (((Bancomat) obs).getListaBanconote().get(10).getNumPezzi() < 50)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 10 + " " + ((Bancomat) obs).getListaBanconote().get(10).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(10)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 10");
                    }
                }
                this.listaNotifiche.add(a);
            }
            if (((Bancomat) obs).getListaBanconote().get(20).getNumPezzi() < 25)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 20 + " " + ((Bancomat) obs).getListaBanconote().get(20).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(20)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 20");
                    }
                }
                this.listaNotifiche.add(a);
            }
            if (((Bancomat) obs).getListaBanconote().get(50).getNumPezzi() < 10)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 50 + " " + ((Bancomat) obs).getListaBanconote().get(50).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(50)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 50");
                    }
                }
                this.listaNotifiche.add(a);
            }
            if (((Bancomat) obs).getListaBanconote().get(100).getNumPezzi() < 5)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 100 + " " + ((Bancomat) obs).getListaBanconote().get(100).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(100)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 100");
                    }
                }
                this.listaNotifiche.add(a);
            }
            if(((Bancomat) obs).getListaBanconote().get(200).getNumPezzi() < 2)
            {
                a = String.valueOf(((Bancomat) obs).getCodice() + " " + 200 + " " + ((Bancomat) obs).getListaBanconote().get(200).getNumPezzi());
                for(int i=0; i<this.listaNotifiche.size();i++)
                {
                    splitStr = this.listaNotifiche.get(i).split("\\s+");
                    if(splitStr[0].equals(String.valueOf(((Bancomat) obs).getCodice())) && splitStr[1].equals(String.valueOf(200)))
                    {
                        this.listaNotifiche.remove(this.listaNotifiche.get(i));
                        System.out.println("Ho Trovato un doppione 200");
                    }
                }
                this.listaNotifiche.add(a);
            }
            notificaBanconote();


        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void notificaBanconote() throws IOException
    {

        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\notificheDipendenteT.txt");
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        /** Per ogni elemento della lista fa una stampa
        *
        * */
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
