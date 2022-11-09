import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.unict.domain.BancaISA;
import org.unict.domain.Cliente;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class BancaISATest {
    static BancaISA bancaISA;

    @BeforeClass
    public static void initTest() throws IOException {bancaISA = BancaISA.getInstance();}

    @Test
    public void testLoadUtenti() {
        //verifica che una volta caricato dal file (non vuoto) l’elenco degli utenti
        // le istanze di utenti non risultino “NULL”
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
}
