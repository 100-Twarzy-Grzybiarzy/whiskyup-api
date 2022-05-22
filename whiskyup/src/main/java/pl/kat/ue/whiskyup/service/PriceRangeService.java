package pl.kat.ue.whiskyup.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceRangeService {

    private final static Double LIMIT_1 = 0.0;
    private final static Double LIMIT_2 = 50.0;
    private final static Double LIMIT_3 = 100.0;
    private final static Double LIMIT_4 = 200.0;
    private final static Double LIMIT_5 = 400.0;
    private final static Double LIMIT_6 = 800.0;
    private final static Double LIMIT_7 = 1600.0;
    private final static Double LIMIT_8 = 3200.0;

    private final static String RANGE_1 = "0-50";
    private final static String RANGE_2 = "50-100";
    private final static String RANGE_3 = "100-200";
    private final static String RANGE_4 = "200-400";
    private final static String RANGE_5 = "400-800";
    private final static String RANGE_6 = "800-1600";
    private final static String RANGE_7 = "1600-3200";
    private final static String RANGE_8 = ">3200";

    private final static NavigableMap<Double, String> priceRanges;

    static {
        priceRanges = new TreeMap<>();
        priceRanges.put(LIMIT_1, RANGE_1);
        priceRanges.put(LIMIT_2, RANGE_2);
        priceRanges.put(LIMIT_3, RANGE_3);
        priceRanges.put(LIMIT_4, RANGE_4);
        priceRanges.put(LIMIT_5, RANGE_5);
        priceRanges.put(LIMIT_6, RANGE_6);
        priceRanges.put(LIMIT_7, RANGE_7);
        priceRanges.put(LIMIT_8, RANGE_8);
    }

    public static String getPriceRange(Double price) {
        return Optional.ofNullable(price)
                .map(p -> priceRanges.lowerEntry(p).getValue())
                .orElse(null);
    }

    public List<String> getPriceRanges() {
        return new ArrayList<>(priceRanges.values());
    }
}