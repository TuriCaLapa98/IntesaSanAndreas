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

public class ContoCorrenteTest
{
    @BeforeClass
    public static void initTest() throws IOException { ContoCorrente conto = ContoCorrente.getInstance(); }

    @Test
    public void testCreaPrelievoFiliale()
    {
        try
        {
            ContoCorrente conto = ContoCorrente.getInstance();
            float importo = 1000000;
            conto.creaPrelievoFiliale(importo);
        }catch (Exception e)
        {
            assertEquals("ERRORE: L'IMPORTO INSERITO E' SUPERIORE AL SALDO", e.getMessage());
        }
    }

    @Test
    public void testCreaDeposito()
    {
        try
        {
            ContoCorrente conto = ContoCorrente.getInstance();
            float importo = 1000;
            String nomeM = "Mario";
            String cognomeM = "Rossi";
            String iban = "IT88A5315876888350827758213";
            conto.creaDeposito(importo, nomeM, cognomeM, iban);
        }
        catch (Exception e)
        {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void testCreaPrestito()
    {
        try
        {
            ContoCorrente conto = ContoCorrente.getInstance();
            float importo = 1000000;
            float stipendio = 500;
            String iban = "IT88A5315876888350827758213";
            conto.creaPrestito(importo, stipendio, iban);
        }
        catch (Exception e)
        {
            assertEquals("ERRORE: L'importo inserito è troppo alto in proporzione allo stipendio", e.getMessage());
        }
    }


}