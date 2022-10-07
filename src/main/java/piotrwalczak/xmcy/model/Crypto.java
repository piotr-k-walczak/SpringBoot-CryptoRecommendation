package piotrwalczak.xmcy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Crypto {

    private final String symbol;

    private List<PriceRecord> priceRecords;

    public Crypto(String symbol) {
        this.symbol = symbol;
        this.priceRecords = new ArrayList<>();
    }

    /**
     * Adds a price record
     */
    public void addPriceRecord(PriceRecord priceRecord) {
        this.priceRecords.add(priceRecord);
    }

    /**
     * Returns a list of price records
     */
    public List<PriceRecord> getPriceRecords() {
        return priceRecords;
    }

    /**
     * Sets a list of price records
     */
    public void setPriceRecords(List<PriceRecord> priceRecords) {
        this.priceRecords = priceRecords;
    }

    /**
     * Returns a symbol of the crypto
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * A price record containing a time of the record and its price
     *
     * @param time  time of the record
     * @param price price of the crypto
     */
    public record PriceRecord(Date time, double price) {
    }
}
