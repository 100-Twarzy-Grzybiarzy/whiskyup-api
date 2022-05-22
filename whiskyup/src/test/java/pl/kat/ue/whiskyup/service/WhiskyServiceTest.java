package pl.kat.ue.whiskyup.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.WhiskyMapperImpl;
import pl.kat.ue.whiskyup.model.FilterTypeDto;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultDto;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.repository.WhiskyRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WhiskyServiceTest {

    @Mock
    private WhiskyRepository whiskyRepository;

    @Spy
    private WhiskyMapperImpl whiskyBaseMapper;

    @Mock
    private PaginationCursorMapper paginationCursorMapper;

    @InjectMocks
    private WhiskyService whiskyService;

    @Captor
    ArgumentCaptor<Map<String, AttributeValue>> exclusiveStartKeyCaptor;

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @Captor
    ArgumentCaptor<Integer> limitCaptor;

    @Test
    void shouldGetWhiskiesFromOneDay() {
        //given
        SearchWhiskiesDto filterDto = SearchWhiskiesDto.builder().build();
        LocalDate localDate = LocalDate.parse("2022-05-02");
        WhiskiesFindResultDto actual;

        //when
        when(whiskyRepository.getWhiskies(eq(null), eq(LocalDate.parse("2022-05-02")), eq(25)))
                .thenReturn(getOnePage());
        when(paginationCursorMapper.mapFromCursor(null))
                .thenReturn(null);

        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(localDate);
            mockedLocalDate.when(() -> LocalDate.parse("2022-05-02")).thenReturn(localDate);
            actual = whiskyService.getWhiskies(filterDto);
        }

        //then
        Assertions.assertEquals(25, actual.getResults().size());

    }

    @Test
    void shouldGetWhiskiesFromTwoDays() {
        //given
        SearchWhiskiesDto filterDto = SearchWhiskiesDto.builder().build();
        LocalDate localDate = LocalDate.parse("2022-05-02");
        WhiskiesFindResultDto actual;

        //when
        when(whiskyRepository.getWhiskies(eq(null), any(LocalDate.class), anyInt()))
                .thenReturn(getFirstPage(), getSecondPage());
        when(paginationCursorMapper.mapFromCursor(eq(null)))
                .thenReturn(null);
        when(paginationCursorMapper.mapToCursor(eq(getExclusiveStartKey())))
                .thenReturn("tokenAbc");

        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(localDate);
            mockedLocalDate.when(() -> LocalDate.parse("2022-05-02")).thenReturn(localDate);
            actual = whiskyService.getWhiskies(filterDto);
        }

        //then
        verify(whiskyRepository, times(2))
                .getWhiskies(exclusiveStartKeyCaptor.capture(), dateCaptor.capture(), limitCaptor.capture());
        List<Integer> allLimits = limitCaptor.getAllValues();
        List<LocalDate> allDates = dateCaptor.getAllValues();
        List<Map<String, AttributeValue>> allKeys = exclusiveStartKeyCaptor.getAllValues();

        Assertions.assertEquals(LocalDate.parse("2022-05-02"), allDates.get(0));
        Assertions.assertEquals(LocalDate.parse("2022-05-01"), allDates.get(1));
        Assertions.assertEquals(25, allLimits.get(0));
        Assertions.assertEquals(1, allLimits.get(1));
        Assertions.assertNull(allKeys.get(0));
        Assertions.assertNull(allKeys.get(1));

        Assertions.assertEquals("tokenAbc", actual.getPageCursor());
        Assertions.assertEquals(25, actual.getResults().size());
        Assertions.assertEquals("2022-05-02", actual.getResults().get(0).getAddedDate());
    }


    @Test
    void shouldGetWhiskiesFilteredByBrandName() {
        //given
        SearchWhiskiesDto searchDto = SearchWhiskiesDto.builder()
                .pageCursor(null)
                .filter(FilterTypeDto.BRAND)
                .value("Ao")
                .build();

        //when
        when(whiskyRepository.getWhiskiesByBrand(eq(searchDto), anyMap()))
                .thenReturn(getPageWhereBrandIsAo());

        WhiskiesFindResultDto actual = whiskyService.getWhiskies(searchDto);

        //then
        Assertions.assertEquals(getPageWhereBrandIsAo().items().size(), actual.getResults().size());
    }

    @Test
    void shouldGetWhiskiesFilteredByPriceRange() {
        //given
        SearchWhiskiesDto searchDto = SearchWhiskiesDto.builder()
                .pageCursor(null)
                .filter(FilterTypeDto.PRICERANGE)
                .value("800-1600")
                .build();

        //when
        when(whiskyRepository.getWhiskiesByPriceRange(eq(searchDto), anyMap()))
                .thenReturn(getPageWherePriceIdBetween800And1600());

        WhiskiesFindResultDto actual = whiskyService.getWhiskies(searchDto);

        //then
        Assertions.assertEquals(getPageWhereBrandIsAo().items().size(), actual.getResults().size());
    }

    private static Page<Whisky> getPageWhereBrandIsAo() {
        List<Whisky> whiskies = List.of(
                Whisky.builder().brand("Ao").addedDate(LocalDate.parse("2022-04-03")).price(200.0).build(),
                Whisky.builder().brand("Ao").addedDate(LocalDate.parse("2022-05-04")).price(801.0).build(),
                Whisky.builder().brand("Ao").addedDate(LocalDate.parse("2022-05-01")).price(1430.0).build(),
                Whisky.builder().brand("Ao").addedDate(LocalDate.parse("2022-05-02")).price(1200.0).build()
        );
        return Page.create(whiskies);
    }

    private static Page<Whisky> getPageWherePriceIdBetween800And1600() {
        List<Whisky> whiskies = List.of(
                Whisky.builder().brand("Ao").price(1430.0).build(),
                Whisky.builder().brand("Ao").price(1200.0).build(),
                Whisky.builder().brand("Ao").price(801.0).build(),
                Whisky.builder().brand("Black Jack").price(899.0).build()
        );
        return Page.create(whiskies);
    }

    private static Page<Whisky> getOnePage() {
        List<Whisky> whiskies = Stream.generate(() -> Whisky.builder()
                        .addedDate(LocalDate.parse("2022-05-02"))
                        .build())
                .limit(25)
                .collect(Collectors.toList());
        return Page.create(whiskies, null);
    }

    private static Page<Whisky> getFirstPage() {
        List<Whisky> whiskies = Stream.generate(() -> Whisky.builder()
                        .addedDate(LocalDate.parse("2022-05-02"))
                        .build())
                .limit(24)
                .collect(Collectors.toList());
        return Page.create(whiskies, null);
    }

    private static Page<Whisky> getSecondPage() {
        List<Whisky> whiskies = List.of(Whisky.builder().addedDate(LocalDate.parse("2022-05-01")).build());
        return Page.create(whiskies, getExclusiveStartKey());
    }

    private static Map<String, AttributeValue> getExclusiveStartKey() {
        return Map.of("GSI1PK", AttributeValue.fromS("AttributeValue(S=WHISKIES#2022-05-01)"));
    }
}