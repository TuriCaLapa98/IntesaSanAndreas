package org.unict.domain;

import java.io.*;
import java.util.*;

public class BancaISA {
    private static BancaISA bancaIsa;
    private Cliente clienteCorrente;
    private Map<String, Cliente> listaClienti;
    public Map<String, ContoCorrente> listaCc;
    private final BufferedReader tastiera;


    private String iban;

    /* ------- BancaISA ------- */
    private BancaISA() {
        this.listaClienti = new HashMap<>();
        this.listaCc = new HashMap<>();
        this.tastiera = new BufferedReader(new InputStreamReader(System.in));
        caricaClienti();
    }

    /* ------- getInstance ------- */
    public static BancaISA getInstance() {
        if (bancaIsa == null)
            bancaIsa = new BancaISA();

        return bancaIsa;
    }

    /* ------- caricaClienti ------- */
    public void caricaClienti() {
        try {
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoClienti.txt";
            BufferedReader fp = new BufferedReader(new FileReader(file));

            for (String cf = fp.readLine(); cf != null; cf = fp.readLine()) {
                String nome = fp.readLine();
                String cognome = fp.readLine();
                String dataNascita = fp.readLine();
                String email = fp.readLine();
                String telefono = fp.readLine();

                Cliente c = new Cliente(cf, nome, cognome, dataNascita, email, telefono);
                this.listaClienti.put(cf, c);
                if (this.listaClienti == null)
                    throw new Exception("Errore caricamento clienti");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* ------- verificaCredenziali ------- */
    public boolean verificaCredenziali(String cf) throws Exception {
        if (listaClienti.containsKey(cf)) {
            try {
                clienteCorrente = listaClienti.get(cf);
                System.out.println("Cliente esistente, creazione conto corrente in corso...\n");
                // throw new Exception("Cliente esistente, creazione conto corrente in corso...\n");
                clienteCorrente.creaContoCorrente();
                return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            /* ------- UC12 CreaCliente ------- */
            //Chiedere da console se si vuole creare il nuovo cliente

            System.out.println("Cliente non esistente, creazione conto corrente in corso...\n");
            inserisciCredenziali(cf);
            return true;
        }

    }

    /* ------- inserisciCredenziali ------- */
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
        listaClienti.put(cf, clienteCorrente);
        FileWriter file = new FileWriter("D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoClienti.txt");
        BufferedWriter filebuf = new BufferedWriter(file);
        PrintWriter printout = new PrintWriter(filebuf);
        listaClienti.forEach((key, value) -> printout.println
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

    public void menuDipendenteN() throws Exception {
        System.out.println("sono nel menu N");

        int scelta = -1;
        do {
            try {
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
            } catch (Exception ignored) {
            }
            switch (scelta) {
                case 1:
                    /* ------- UC1 CreaContoCorrente ------- */
                    System.out.println("Inserisci codice fiscale");
                    verificaCredenziali(tastiera.readLine());
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
        if (listaCc.get(iban) != null) {
            verificaBeneficiario(iban, nomeBeneficiario, cognomeBeneficiario);
        } else
            System.out.println("IBAN NON ESISTENTE");
    }

    private void verificaBeneficiario(String iban, String nomeBeneficiario, String cognomeBeneficiario) throws Exception {


        if (listaClienti.get(listaCc.get(iban).getCf()).getNome().equals(nomeBeneficiario) &&
                listaClienti.get(listaCc.get(iban).getCf()).getCognome().equals(cognomeBeneficiario)) {
            System.out.println("Inserisci Importo");
            float importo = Float.parseFloat(tastiera.readLine());
            System.out.println("Inserisci Nome Mittente");
            String nomeM = tastiera.readLine();
            System.out.println("Inserisci Cognome Mittente");
            String cognomeM = tastiera.readLine();

            listaCc.get(iban).inserisciImporto(importo, nomeM, cognomeM);
        }

    }


    public void menuDipendenteT() {
        System.out.println("sono nel menu T");

    }

    public void menuCliente() {
        System.out.println("sono nel menu cliente");


        try {
            System.out.println("****INSERIMENTO CREDENZIALI****\n");
            System.out.println("Inserisci iban\n");
            String iban = tastiera.readLine();
            System.out.println("Inserisci pin\n");
            String pin = tastiera.readLine();

            if (listaCc.containsKey(iban) && Objects.equals(listaCc.get(iban).getPin(), pin)) {

                System.out.println("Inserisci importo\n");

                //Il prelievo deve essere divisibile per 5. IMPLEMENTARE CONTROLLO DOPO ACQUISIZIONE PRELIEVO DA TASTIERA, dare la possibilità di uscire dal loop

                int prelievo = Integer.parseInt(tastiera.readLine());
                int pz200 = 0, pz100 = 0, pz50 = 0, pz20 = 0, pz10 = 0, pz5 = 0;
                if (listaCc.get(iban).getSaldo() >= prelievo) {
                    //inziamo dal taglio più grande/ tmp quantita di banconote
                    pz200 = prelievo/200; //dim200=20pz , pz200=25
                    if(pz200 > max200 /*prendere il valore della quantità dei pezzi da 200*/)
                    {
                        //anzichè usare il tmp usare direttamente il valore massimo
                        tmp200=//max200
                        pz100=(max200*200-prelievo)/100 //pz100=5
                        prelievo = prelievo - max200*200;
                    }

                    pz100 = prelievo/100;
                //implementare taglio banconote: lista con valore
                System.out.println("\nPrelevare il denaro richiesto\n");
                }
            }

        }
        catch(
                Exception ignored)

        {


        }

    }






}
