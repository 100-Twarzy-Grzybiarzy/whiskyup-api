package pl.kat.ue.whiskyup.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.kat.ue.whiskyup.utils.manager.JwtManager;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

class PaginationCursorMapperTest {

    private static PaginationCursorMapper mapper;
    private final static String jwtSecretKey =
            "mockSecretJwtKeyUsedWithHs512MustHaveSizeEqualOrGraterThan512Bits" +
            "mockSecretJwtKeyUsedWithHs512MustHaveSizeEqualOrGraterThan512Bits";

    @BeforeAll
    static void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        AwsImmutablePojoMapper pojoMapper = new AwsImmutablePojoMapper(objectMapper);
        JwtManager jwtManager = new JwtManager(jwtSecretKey);
        mapper = new PaginationCursorMapper(pojoMapper, objectMapper, jwtManager);
    }

    @Test
    void shouldMapPaginationCursorToLastEvaluatedKey() {
        //given
        String paginationCursor = getPaginationCursor();

        //when
        Map<String, AttributeValue> actual = mapper.mapFromCursor(paginationCursor);

        //then
        Assertions.assertEquals(getLastEvaluatedKey(), actual);
    }

    @Test
    void shouldReturnNullWhenPaginationCursorIsNull() {
        //given
        String paginationCursor = null;

        //when
        Map<String, AttributeValue> actual = mapper.mapFromCursor(paginationCursor);

        //then
        Assertions.assertNull(actual);
    }

    @Test
    void shouldMapLastEvaluatedKeyToPaginationCursor() {
        //given
        Map<String, AttributeValue> lastEvaluatedKey = getLastEvaluatedKey();

        //when
        String actual = mapper.mapToCursor(lastEvaluatedKey);

        //then
        Assertions.assertEquals(getPaginationCursor(), actual);
    }

    @Test
    void shouldReturnNullWhenLastEvaluatedKeyIsNull() {
        //given
        Map<String, AttributeValue> lastEvaluatedKey = null;

        //when
        String actual = mapper.mapToCursor(lastEvaluatedKey);

        //then
        Assertions.assertNull(actual);
    }

    private String getPaginationCursor() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJjdXJzb3IiOiJ7XCJ5ZWFyXCI6XCJ7XFxcInNcXFwiOlxcXC" +
                "JCZW5ueVxcXCIsXFxcIm5cXFwiOlxcXCIxOTk5XFxcIixcXFwiYlxcXCI6bnVsbCxcXFwi" +
                "c3NcXFwiOm51bGwsXFxcIm5zXFxcIjpudWxsLFxcXCJic1xcXCI6bnVsbCxcXFwibVxcXC" +
                "I6bnVsbCxcXFwibFxcXCI6bnVsbCxcXFwiYm9vbFxcXCI6bnVsbCxcXFwibnVsXFxcIjpu" +
                "dWxsfVwifSJ9.10zvcyP4kEcDLlLcpEEMyqH9e5RIjDdlsznpQmQHsJcuKU7ErnyTT-kwg" +
                "91mU-o2T29wFhpUAPQ7LOINHizAuw";
    }

    private Map<String, AttributeValue> getLastEvaluatedKey() {
        return Map.of("year", AttributeValue.builder().n("1999").s("Benny").build());
    }
}