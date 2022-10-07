package piotrwalczak.xmcy.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;
import piotrwalczak.xmcy.model.PriceRecordStats;
import piotrwalczak.xmcy.service.RecommendationService;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class RecommendationController implements IRecommendationController {

    private final Bucket bucket;
    @Autowired
    private RecommendationService recommendationService;

    RecommendationController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(60)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public ResponseEntity<List<String>> getCryptosSortedByNormalizedRange() throws UnsupportedSymbolException {
        return bucket.tryConsume(1) ? ResponseEntity.ok(recommendationService.getCryptosSortedByNormalizedRange()) : ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @Override
    public ResponseEntity<List<String>> getCryptosSortedByNormalizedRangeWithinTimePeriod(@RequestParam Date startDate, @RequestParam Date endDate) throws UnsupportedSymbolException {
        return bucket.tryConsume(1) ? ResponseEntity.ok(recommendationService.getCryptosSortedByNormalizedRangeWithingTimePeriod(startDate, endDate)) : ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @Override
    public ResponseEntity<PriceRecordStats> getStatsForCrypto(@RequestParam String symbol, @RequestParam Date startDate, @RequestParam Date endDate) throws UnsupportedSymbolException {
        Optional<PriceRecordStats> recordStats = Optional.ofNullable(recommendationService.getPriceRecordStatsWithinTimePeriod(symbol, startDate, endDate));
        return bucket.tryConsume(1) ? recordStats.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build()) : ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @Override
    public ResponseEntity<PriceRecordStats> getStatsForCrypto(@RequestParam String symbol) throws UnsupportedSymbolException {
        Optional<PriceRecordStats> recordStats = Optional.ofNullable(recommendationService.getPriceRecordStats(symbol));
        return bucket.tryConsume(1) ? recordStats.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build()) : ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @Override
    public ResponseEntity<String> getCryptoWithHighestNormalizedRange(@RequestParam Date day) throws UnsupportedSymbolException {
        Optional<String> symbol = Optional.ofNullable(recommendationService.getCryptoWithHighestNormalizedRange(day));
        return bucket.tryConsume(1) ? symbol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.internalServerError().build()) : ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
