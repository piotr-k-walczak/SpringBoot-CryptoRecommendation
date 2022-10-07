package piotrwalczak.xmcy.dao;

import com.opencsv.CSVReader;
import piotrwalczak.xmcy.model.Crypto;

import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CsvLoader {

    public static Map<String, Crypto> loadCryptoRecordsFromDirectory(String directory) {
        List<File> recordFiles = Stream.of(new File(directory).listFiles())
                .filter(File::isFile)
                .filter(file -> file.getName().contains("_values.csv"))
                .toList();

        Map<String, Crypto> cryptoRecordsMap = new HashMap<>();
        for (File file : recordFiles) {
            Crypto crypto = loadCryptoRecordsFromFile(file);
            String symbol = file.getName().replace("_values.csv", "");
            cryptoRecordsMap.put(symbol, crypto);
        }
        return cryptoRecordsMap;
    }

    public static Crypto loadCryptoRecordsFromFile(File file) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> r = reader.readAll();
            if (!r.isEmpty()) {
                String symbol = r.get(1)[1];
                Crypto crypto = new Crypto(symbol);
                r.forEach(x -> {
                    try {
                        if (!x[0].equals("timestamp")) {
                            Date date = new Date(Long.parseLong(x[0]));
                            double value = Double.parseDouble(x[2]);
                            crypto.addPriceRecord(new Crypto.PriceRecord(date, value));
                        }
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                });
                return crypto;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
