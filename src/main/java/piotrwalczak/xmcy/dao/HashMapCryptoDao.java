package piotrwalczak.xmcy.dao;

import org.springframework.stereotype.Service;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.Crypto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static piotrwalczak.xmcy.utils.Utils.toInstantDay;

@Service
public class HashMapCryptoDao implements CryptoDao {

    private final Map<String, Crypto> cryptoRecords;

    public HashMapCryptoDao() {
        this.cryptoRecords = CsvLoader.loadCryptoRecordsFromDirectory("src/main/resources/data");
    }

    @Override
    public List<String> supportedSymbols() {
        return cryptoRecords.keySet().stream().toList();
    }

    @Override
    public List<Crypto.PriceRecord> findPriceRecordsForDay(String symbol, Date date) throws UnsupportedSymbolException {
        try {
            Crypto crypto = cryptoRecords.get(symbol);
            return crypto.getPriceRecords().stream()
                    .filter(record -> toInstantDay(record.time()).equals(toInstantDay(date)))
                    .collect(Collectors.toList());
        } catch (NullPointerException exception) {
            throw new UnsupportedSymbolException(symbol);
        }
    }

    @Override
    public List<Crypto.PriceRecord> findPriceRecordsForTimePeriod(String symbol, Date startDate, Date endDate) throws UnsupportedSymbolException {
        try {
            Crypto crypto = cryptoRecords.get(symbol);
            return crypto.getPriceRecords().stream()
                    .filter(record -> !toInstantDay(record.time()).isBefore(toInstantDay(startDate)))
                    .filter(record -> !toInstantDay(record.time()).isAfter(toInstantDay(endDate)))
                    .collect(Collectors.toList());
        } catch (NullPointerException exception) {
            throw new UnsupportedSymbolException(symbol);
        }
    }

    @Override
    public List<Crypto.PriceRecord> findAllPriceRecords(String symbol) throws UnsupportedSymbolException {
        try {
            Crypto crypto = cryptoRecords.get(symbol);
            return crypto.getPriceRecords();
        } catch (NullPointerException exception) {
            throw new UnsupportedSymbolException(symbol);
        }
    }

    public boolean isSymbolSupported(String symbol) {
        return cryptoRecords.containsKey(symbol);
    }
}
