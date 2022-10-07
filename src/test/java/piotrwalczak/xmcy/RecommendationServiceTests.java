package piotrwalczak.xmcy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import piotrwalczak.xmcy.dao.CryptoDao;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.Crypto;
import piotrwalczak.xmcy.model.PriceRecordStats;
import piotrwalczak.xmcy.service.RecommendationService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecommendationServiceTests {

    @Autowired
    private RecommendationService recommendationService;

    @MockBean
    private CryptoDao cryptoDao;

    @Test
    public void getStatsFromRecords_shouldReturnMin3Max8Oldest5Newest7() throws UnsupportedSymbolException {
        List<Crypto.PriceRecord> records = List.of(
                new Crypto.PriceRecord(new Date(1), 5),
                new Crypto.PriceRecord(new Date(2), 8),
                new Crypto.PriceRecord(new Date(3), 3),
                new Crypto.PriceRecord(new Date(4), 7)
        );

        when(cryptoDao.findAllPriceRecords("BTC")).thenReturn(records);
        PriceRecordStats priceRecordStats = recommendationService.getStatsFromRecords(records);
        assertEquals(priceRecordStats.min(), 3);
        assertEquals(priceRecordStats.max(), 8);
        assertEquals(priceRecordStats.oldest(), 5);
        assertEquals(priceRecordStats.newest(), 7);
    }
}
