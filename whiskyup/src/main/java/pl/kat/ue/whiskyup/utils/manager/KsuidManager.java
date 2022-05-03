package pl.kat.ue.whiskyup.utils.manager;

import com.github.ksuid.KsuidGenerator;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class KsuidManager {

    private final static KsuidGenerator ksuidGenerator = new KsuidGenerator(new SecureRandom());

    public static String newKsuid() {
        return ksuidGenerator.newKsuid().toString();
    }

    public static String newKsuid(LocalDate localDate) {
        Instant instantForId = localDate.atTime(LocalTime.now()).toInstant(ZoneOffset.UTC);
        return ksuidGenerator.newKsuid(instantForId).toString();
    }
}