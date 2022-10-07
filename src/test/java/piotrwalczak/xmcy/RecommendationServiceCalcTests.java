package piotrwalczak.xmcy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import piotrwalczak.xmcy.dao.CryptoDao;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.Crypto;
import piotrwalczak.xmcy.model.PriceRecordStats;
import piotrwalczak.xmcy.service.RecommendationService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecommendationServiceCalcTests {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CryptoDao cryptoDao;

    @Test
    public void givenMin2Max4_normalizedRangeShouldEqual1(){
        assertEquals(recommendationService.normalizedRange(2, 4),1);
    }

    @Test
    public void givenMin2Max4InPriceRecordStats_normalizedRangeShouldEqual1(){
        PriceRecordStats priceRecordStats = new PriceRecordStats(1, 6, 2, 4);
        assertEquals(recommendationService.normalizedRange(priceRecordStats), 1);
    }

    @Test
    public void givenUnsupportedSymbol_shouldThrowUnsupportedSymbolException(){
        assertThrows(UnsupportedSymbolException.class, () -> {
            cryptoDao.findAllPriceRecords("UNSUPPORTED");
        });
    }
}
