import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import org.unict.domain.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

public class BancaISATest
{
    @BeforeClass
    public static void initTest() throws IOException { BancaISA bancaISA = BancaISA.getInstance(); }

    @Test
    public void testCaricaClienti()
    {
        //verifica che una volta caricato dal file (non vuoto) l’elenco dei clienti
        //le istanze di utenti non risultino “NULL”
        try{
            String file = FilePaths.ELENCO_CLIENTI_PATH;
            BufferedReader fp = new BufferedReader(new FileReader(file));
            for (String cf = fp.readLine(); cf != null; cf = fp.readLine()) {
                String nome = fp.readLine();
                String cognome = fp.readLine();
                String dataNascita = fp.readLine();
                String email = fp.readLine();
                String telefono = fp.readLine();

                Cliente c = new Cliente(cf, nome, cognome, dataNascita, email, telefono);
                assertNotNull(c);
            }}catch (IOException e){
            fail("Unexpected exception!");
        }
    }
    @Test
    public void testVerificaEsistenzaCc()
    {
        //Mi aspetto che non trovi l'iban
        try
        {
            BancaISA bancaISA = BancaISA.getInstance();
            bancaISA.verificaEsistenzaCc("Iban di prova", "CF di prova", "Prelievo");
        }
        catch (Exception e)
        {
            assertEquals("ERRORE: IBAN NON ESISTENTE", e.getMessage());
        }
    }

    @Test
    //Per verificare che funzioni bisogna togliere le notifiche del file
    public void testObserver() throws IOException, InterruptedException
    {
        BancaISA bancaISA = BancaISA.getInstance();
        int notificheIniziali = bancaISA.listaNotifiche.size();
        bancaISA.listaBancomat.get(0).calcolaPrelievo(1000);
        bancaISA.aggiornaFileBanconote();
        int notificheFinali = bancaISA.listaNotifiche.size();

        assertEquals(notificheIniziali + 1,notificheFinali);
    }
}