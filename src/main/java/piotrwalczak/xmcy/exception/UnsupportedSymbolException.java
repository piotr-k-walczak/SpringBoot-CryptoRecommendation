package piotrwalczak.xmcy.exception;

/**
 * Thrown if provided symbol is not supported
 */
public class UnsupportedSymbolException extends Exception {

    public UnsupportedSymbolException(String symbol) {
        super("Provided symbol < " + symbol + " > is not yet supported.");
    }
}
