package piotrwalczak.xmcy.dao;

import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.Crypto;

import java.util.Date;
import java.util.List;

public interface CryptoDao {

    /**
     * Returns a list of supported symbols
     */
    List<String> supportedSymbols();

    /**
     * Returns a list of price records for provided symbol and date
     *
     * @param symbol symbol of the crypto
     * @param date   day of the records
     * @return List of price records
     * @throws UnsupportedSymbolException
     */
    List<Crypto.PriceRecord> findPriceRecordsForDay(String symbol, Date date) throws UnsupportedSymbolException;

    /**
     * Returns a list of price records for provided time period
     *
     * @param symbol    symbol of the crypto
     * @param startDate first day of the time period
     * @param endDate   last day of the time period
     * @return List of price records
     * @throws UnsupportedSymbolException
     */
    List<Crypto.PriceRecord> findPriceRecordsForTimePeriod(String symbol, Date startDate, Date endDate) throws UnsupportedSymbolException;

    /**
     * Returns a list of all price records for provided symbol
     *
     * @param symbol symbol of the crypto
     * @return list of price records
     * @throws UnsupportedSymbolException
     */
    List<Crypto.PriceRecord> findAllPriceRecords(String symbol) throws UnsupportedSymbolException;

    /**
     * Checks whether the symbol is supported
     *
     * @param symbol symbol of the crypto
     */
    boolean isSymbolSupported(String symbol);
}
