package org.unict.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String [] args) throws Exception
    {
        BancaISA bancaISA = BancaISA.getInstance();
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        int scelta =-1;
        String identificatore;
        InterfacciaLogin loginDipendente = new LoginDipendenti();
        InterfacciaLogin loginCliente = new LoginBancomat();


        /* ---------------- Menu ---------------- */
        do
        {
            try
            {
                System.out.println("\n\nInserisci la tua scelta:" + "\n1) Login" + " \n0) Chiudi applicazione");
                scelta = Integer.parseInt(tastiera.readLine());
            }catch (Exception e){e.printStackTrace();}

            if(scelta==1)
            {
                loginDipendente.setMetodoAccesso(loginCliente);
                try{
                    System.out.println("Inserisci: \n" +
                            " DN - Dipendente Normale\n" +
                            " DT - Dipendente Tecnico\n" +
                            " Invio - Accedere Al Bancomat\n");
                    identificatore = tastiera.readLine();
                    loginDipendente.login(identificatore);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }while(scelta !=0);

        System.out.println("Grazie per aver scelto BancaISA!");
    }
}
