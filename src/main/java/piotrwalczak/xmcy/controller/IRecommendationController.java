package piotrwalczak.xmcy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.PriceRecordStats;

import java.util.Date;
import java.util.List;

@RequestMapping("/crypto")
public interface IRecommendationController {

    /**
     * Returns a descending sorted list of all the cryptos,
     * comparing the normalized range -> (max-min)/min
     * using all the data
     *
     * @return sorted list of all the cryptos
     */

    @GetMapping("/ranking")
    ResponseEntity<List<String>> getCryptosSortedByNormalizedRange() throws UnsupportedSymbolException;

    /**
     * Returns a descending sorted list of all the cryptos,
     * comparing the normalized range -> (max-min)/min
     * within provided time period
     *
     * @param startDate first day included
     * @param endDate   last day included
     * @return sorted list of all the cryptos
     */
    @GetMapping("/ranking/period")
    ResponseEntity<List<String>> getCryptosSortedByNormalizedRangeWithinTimePeriod(Date startDate, Date endDate) throws UnsupportedSymbolException;

    /**
     * Returns oldest/newest/min/max values for a requested crypto
     *
     * @param symbol    symbol name of the crypto (i.e. BTC)
     * @param startDate first day included
     * @param endDate   last day included
     * @return oldest/newest/min/max values for a requested crypto
     * @throws UnsupportedSymbolException when there is no CSV file available for the provided symbol
     */
    @GetMapping("/stats/period")
    ResponseEntity<PriceRecordStats> getStatsForCrypto(String symbol, Date startDate, Date endDate) throws UnsupportedSymbolException;

    /**
     * Returns oldest/newest/min/max values for a requested crypto
     *
     * @param symbol symbol name of the crypto (i.e. BTC)
     * @return oldest/newest/min/max values for a requested crypto
     * @throws UnsupportedSymbolException when there is no CSV file available for the provided symbol
     */
    @GetMapping("/stats")
    ResponseEntity<PriceRecordStats> getStatsForCrypto(String symbol) throws UnsupportedSymbolException;

    /**
     * Returns crypto with the highest normalized range for provided day
     *
     * @param day checked day
     * @return name of crypto with the highest normalized range
     * @throws UnsupportedSymbolException when there is no CSV file available for the provided symbol
     */
    @GetMapping("/highest")
    ResponseEntity<String> getCryptoWithHighestNormalizedRange(Date day) throws UnsupportedSymbolException;
}
