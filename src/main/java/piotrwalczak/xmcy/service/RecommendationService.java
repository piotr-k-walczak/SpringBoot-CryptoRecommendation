package piotrwalczak.xmcy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrwalczak.xmcy.dao.CryptoDao;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.Crypto;
import piotrwalczak.xmcy.model.PriceRecordStats;

import java.util.*;

@Service
public class RecommendationService {

    @Autowired
    private CryptoDao cryptoDao;

    /**
     * Based on min and max value calculates the normalized range
     *
     * @param min
     * @param max
     * @return normalized range
     */
    public double normalizedRange(double min, double max) {
        return (max - min) / min;
    }

    /**
     * Based on PriceRecordStats calculate the normalized range
     *
     * @param priceRecordStats
     * @return
     */
    public double normalizedRange(PriceRecordStats priceRecordStats) {
        return normalizedRange(priceRecordStats.min(), priceRecordStats.max());
    }

    /**
     * Returns all time PriceRecordStats for provided symbol
     *
     * @param symbol symbol of the crypto
     * @return PriceRecordStats for the crypto
     * @throws UnsupportedSymbolException
     */
    public PriceRecordStats getPriceRecordStats(String symbol) throws UnsupportedSymbolException {
        return getStatsFromRecords(cryptoDao.findAllPriceRecords(symbol));
    }

    /**
     * Returns all time PriceRecordStats for provided symbol within the time period
     *
     * @param symbol    symbol of the crypto
     * @param startDate first day included
     * @param endDate   last day included
     * @return PriceRecordStats for the crypto
     * @throws UnsupportedSymbolException
     */
    public PriceRecordStats getPriceRecordStatsWithinTimePeriod(String symbol, Date startDate, Date endDate) throws UnsupportedSymbolException {
        return getStatsFromRecords(cryptoDao.findPriceRecordsForTimePeriod(symbol, startDate, endDate));
    }

    /**
     * Extracts PriceRecordStats from list of price records
     *
     * @param priceRecords list of price records
     * @return PriceRecordStats
     */
    public PriceRecordStats getStatsFromRecords(List<Crypto.PriceRecord> priceRecords) {
        double oldest = priceRecords.stream().min(Comparator.comparingDouble(a -> a.time().getTime())).get().price();
        double newest = priceRecords.stream().max(Comparator.comparingDouble(a -> a.time().getTime())).get().price();
        double min = priceRecords.stream().map(Crypto.PriceRecord::price).min(Double::compareTo).get();
        double max = priceRecords.stream().map(Crypto.PriceRecord::price).max(Double::compareTo).get();
        return new PriceRecordStats(oldest, newest, min, max);
    }

    /**
     * Returns a sorted list of cryptos based on their normalized range
     *
     * @return list of crypto symbols
     * @throws UnsupportedSymbolException
     */
    public List<String> getCryptosSortedByNormalizedRange() throws UnsupportedSymbolException {
        Map<String, Double> cryptoWithNormRange = new HashMap<>();
        for (String symbol : cryptoDao.supportedSymbols()) {
            double range = normalizedRange(getStatsFromRecords(cryptoDao.findAllPriceRecords(symbol)));
            cryptoWithNormRange.put(symbol, range);
        }
        return cryptoWithNormRange.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Returns a sorted list of cryptos based on their normalized range for specified time period
     *
     * @param startDate first day included
     * @param endDate   last day included
     * @return list of crypto symbols
     * @throws UnsupportedSymbolException
     */
    public List<String> getCryptosSortedByNormalizedRangeWithingTimePeriod(Date startDate, Date endDate) throws UnsupportedSymbolException {
        Map<String, Double> cryptoWithNormRange = new HashMap<>();
        for (String symbol : cryptoDao.supportedSymbols()) {
            double range = normalizedRange(getStatsFromRecords(cryptoDao.findPriceRecordsForTimePeriod(symbol, startDate, endDate)));
            cryptoWithNormRange.put(symbol, range);
        }
        return cryptoWithNormRange.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Returns a sorted list of cryptos based on their normalized range for specified day
     *
     * @param day
     * @return list of crypto symbols
     * @throws UnsupportedSymbolException
     */
    public String getCryptoWithHighestNormalizedRange(Date day) throws UnsupportedSymbolException {
        String res = null;
        double best = Double.NEGATIVE_INFINITY;
        for (String symbol : cryptoDao.supportedSymbols()) {
            double range = normalizedRange(getStatsFromRecords(cryptoDao.findPriceRecordsForDay(symbol, day)));
            if (range > best) {
                res = symbol;
                best = range;
            }
        }
        return res;
    }
}
