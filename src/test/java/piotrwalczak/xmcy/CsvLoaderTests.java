package piotrwalczak.xmcy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import piotrwalczak.xmcy.dao.CsvLoader;
import piotrwalczak.xmcy.model.Crypto;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CsvLoaderTests {

    @Test
    public void afterLoadingRecords_shouldReturnCryptoWithSymbolBTC(){
        Crypto crypto = CsvLoader.loadCryptoRecordsFromFile(new File("src/test/resources/data/BTC_values.csv"));
        assertEquals("BTC", crypto.getSymbol());
        assertFalse(crypto.getPriceRecords().isEmpty());
    }

    @Test
    public void givenIncorrectFilePath_shouldThrowRuntimeException(){
        assertThrows(RuntimeException.class, () -> {
            Crypto crypto = CsvLoader.loadCryptoRecordsFromFile(new File("src/test/resources/data/incorrect.csv"));
        });
    }
}
