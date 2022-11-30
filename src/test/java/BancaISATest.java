import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import org.unict.domain.BancaISA;
import org.unict.domain.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

public class BancaISATest
{
    @BeforeClass
    public static void initTest() throws IOException {BancaISA bancaISA = BancaISA.getInstance();}

    @Test
    public void testCaricaClienti() {
        //verifica che una volta caricato dal file (non vuoto) l’elenco dei clienti
        //le istanze di utenti non risultino “NULL”
        try{
            String file = "D:\\OneDrive - Università degli Studi di Catania\\Magistrale\\Primo Anno\\Ingegneria del Software\\Esame\\Progetto\\IntesaSanAndreas\\src\\main\\java\\org\\unict\\domain\\Filetxt\\elencoClienti.txt";
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
    public void TestVerificaEsistenzaCc()
    {
        try
        {
            BancaISA bancaISA = BancaISA.getInstance();
            bancaISA.verificaEsistenzaCc("9ufge", "nomebeneficiario", "cognome beneficiario");
            System.out.println("1");
        }
        catch (Exception e)
        {
            System.out.println("2");
            assertEquals("IBAN NON ESISTENTE", e.getMessage());
        }
    }
}