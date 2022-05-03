package pl.kat.ue.whiskyup.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceRangeServiceTest {

    @Test
    void shouldGetFrom0To50PriceRange() {
        //given
        Double price = 40.00;

        //when
        String actual = PriceRangeService.getPriceRange(price);

        //then
        Assertions.assertEquals("0-50", actual);
    }

    @Test
    void shouldGetGreaterThan3200PriceRange() {
        //given
        Double price = 3200.01;

        //when
        String actual = PriceRangeService.getPriceRange(price);

        //then
        Assertions.assertEquals(">3200", actual);
    }

    @Test
    void shouldGetFrom400To800PriceRange() {
        //given
        Double price = 800.00;

        //when
        String actual = PriceRangeService.getPriceRange(price);

        //then
        Assertions.assertEquals("400-800", actual);
    }
}