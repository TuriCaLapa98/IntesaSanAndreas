package org.unict.domain;

import java.io.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

//Observer Pattern
public class BancaISA implements Observer{
    private static BancaISA bancaIsa;
    private Cliente clienteCorrente;
    private ContoCorrente ccCorrente;
    private Map<String, Cliente> listaClienti;
    public Map<String, ContoCorrente> listaCc;
    private final BufferedReader tastiera;
    private Bancomat bancomat;
    public LinkedList<Bancomat> listaBancomat;

    private String iban;
    public Map<Integer, Banconota> listaBanconote;

    /** ----------------------- BancaISA -------------------------- */
    private BancaISA() {
        this.listaClienti = new HashMap<>();
        this.listaCc = new HashMap<>();
        this.listaBanconote = new HashMap<>();
        this.listaBancomat = new LinkedList<>();
        this.tastiera = new BufferedReader(new InputStreamReader(System.in));
        caricaClienti();
    }


    /** ------------------- getInstance ------------------------ */
    public static BancaISA getInstance() {
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
            try {
                clienteCorrente = this.listaClienti.get(cf);
                System.out.println("Cliente esistente, creazione conto corrente in corso...\n");
                // throw new Exception("Cliente esistente, creazione conto corrente in corso...\n");
                clienteCorrente.creaContoCorrente();
                return false;
            } catch (Exception e) {
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
                System.out.println("****MENU DIPENDENTE NORMALE****\n" +
                        "\nInserisci la tua scelta:" +
                        "\n1) Crea conto corrente" +
                        "\n2) Deposito" +
                        "\n0) Esci");
                scelta = Integer.parseInt(tastiera.readLine());
                if (scelta < 0 || scelta > 2) { //Aggiornare man mano che implementiamo i casi d'uso
                    System.out.println("Scelta non valida");
                    throw new Exception("Scelta non valida");
                }
            } catch(Exception e){e.printStackTrace();}

            switch (scelta) {
                case 1:
                    /* ------- UC1 CreaContoCorrente ------- */
                    System.out.println("Inserisci codice fiscale");
                    verificaCredenziali(tastiera.readLine()); //verifichiamo le credenziali del cliente
                    break;

                case 2:
                    /* ------- UC5 Deposito ------- */
                    System.out.println("Inserisci IBAN");
                    String IBAN = tastiera.readLine();
                    System.out.println("Inserisci Nome Beneficiario");
                    String NomeBeneficiario = tastiera.readLine();
                    System.out.println("Inserisci Cognome Beneficiario");
                    String CognomeBeneficiario = tastiera.readLine();
                    verificaEsistenzaCc(IBAN, NomeBeneficiario, CognomeBeneficiario);
                    break;

                default:
                    break;
            }
        } while (scelta != 0);
    }

    private void verificaEsistenzaCc(String iban, String nomeBeneficiario, String cognomeBeneficiario) throws Exception {
        if (this.listaCc.get(iban) != null) {
            verificaBeneficiario(iban, nomeBeneficiario, cognomeBeneficiario);
        } else
            System.out.println("IBAN NON ESISTENTE");
    }

    private void verificaBeneficiario(String iban, String nomeBeneficiario, String cognomeBeneficiario) throws Exception {


        if (this.listaClienti.get(this.listaCc.get(iban).getCf()).getNome().equals(nomeBeneficiario) &&
                this.listaClienti.get(this.listaCc.get(iban).getCf()).getCognome().equals(cognomeBeneficiario))
        {
            System.out.println("Inserisci Importo");
            float importo = Float.parseFloat(tastiera.readLine());
            System.out.println("Inserisci Nome Mittente");
            String nomeM = tastiera.readLine();
            System.out.println("Inserisci Cognome Mittente");
            String cognomeM = tastiera.readLine();

            this.listaCc.get(iban).inserisciImporto(importo, nomeM, cognomeM);
        }

    }

    /** --------------- Menu Dipendente Tecnico ----------------------- */
    public void menuDipendenteT(String notifica) {
        System.out.println("sono nel menu T");
        System.out.println(notifica);
    }

    /** --------------- Menu Bancomat ----------------------- */
    public void menuCliente() {
        int idBancomat = 0;
        caricaListaBancomat();
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
                System.out.println("Saldo disponibile: " + this.listaCc.get(iban).getSaldo());

                int prelievo = inserisciImporto();

                //Verifichiamo se il nostro saldo ci basta per poter effetturare il prelievo
                if(this.listaCc.get(iban).getSaldo() >=prelievo)
                {
                    bancomat.calcolaPrelievo(prelievo);
                    aggiornaFileBanconote();
                    diminuisciSaldo(iban,prelievo);
                } else {
                    System.out.println("Non hai abbastanza soldi nel conto da prelevare\n");
                    bancaIsa.menuCliente();
                }
            }
            else System.out.println("IBAN NON TROVATO O CREDENZIALI ERRATE\n");

        }
        catch(Exception e){e.printStackTrace();}
    }

    private void diminuisciSaldo(String iban, int prelievo) throws IOException
    {

        this.listaCc.get(iban).setSaldo ((int) (this.listaCc.get(iban).getSaldo()-prelievo));

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

    private void aggiornaFileBanconote() throws IOException {

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
                "1) €100 \n" +
                "2) €200 \n" +
                "3) €300 \n" +
                "5) €500 \n" +
                "7) €700 \n" +
                "10) €1000 \n");
        int scelta = Integer.parseInt(tastiera.readLine());
        if(scelta==1 || scelta==2 || scelta==3 || scelta==5 || scelta==7 || scelta==10)
        {
            return scelta*100;
        }else {
            System.out.println("valore inserito non valido\n");
            bancaIsa.inserisciImporto();
        }
        return 0;
    }

    public void caricaListaBancomat() {
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
                    throw new Exception("Errore caricamento Bancomat");
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    //Observer Pattern
    @Override
    public void update(Observable obs, Object arg)
    {
        System.out.println("E' partito l'observer\n");
        try
        {
            String s = "";

            if(((Bancomat) obs).getListaBanconote().get(5).getNumPezzi() < 200)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 5 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(5).getNumPezzi() + " pezzi;\n";

            if(((Bancomat) obs).getListaBanconote().get(10).getNumPezzi() < 100)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 10 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(10).getNumPezzi() + " pezzi;\n";

            if(((Bancomat) obs).getListaBanconote().get(20).getNumPezzi() < 50)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 20 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(20).getNumPezzi() + " pezzi;\n";

            if (((Bancomat) obs).getListaBanconote().get(50).getNumPezzi() < 20)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 50 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(50).getNumPezzi() + " pezzi;\n";

            if (((Bancomat) obs).getListaBanconote().get(100).getNumPezzi() < 10)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 100 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(100).getNumPezzi() + " pezzi;\n";

            if(((Bancomat) obs).getListaBanconote().get(200).getNumPezzi() < 5)

                s = s + "Nel bancomat " + ((Bancomat) obs).getCodice() + " le banconote da €" + 200 + " sono ridotte a " + ((Bancomat) obs).getListaBanconote().get(200).getNumPezzi() + " pezzi;\n";

            notificaBanconote(s);

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void notificaBanconote(String s) throws IOException
    {

        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\notificheDipendenteT.txt");
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);

        printout.println (s);

        printout.flush();
        printout.close();

    }
}
