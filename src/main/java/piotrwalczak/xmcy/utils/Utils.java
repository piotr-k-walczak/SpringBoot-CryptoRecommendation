package piotrwalczak.xmcy.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utils {
    public static Instant toInstantDay(Date date) {
        return date.toInstant().truncatedTo(ChronoUnit.DAYS);
    }
}
