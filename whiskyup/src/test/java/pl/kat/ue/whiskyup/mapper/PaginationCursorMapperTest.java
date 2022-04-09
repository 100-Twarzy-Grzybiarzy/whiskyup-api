package pl.kat.ue.whiskyup.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.kat.ue.whiskyup.utils.JwtManager;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

class PaginationCursorMapperTest {

    private static PaginationCursorMapper mapper;

    @BeforeAll
    static void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        AwsImmutablePojoMapper pojoMapper = new AwsImmutablePojoMapper(objectMapper);
        JwtManager jwtManager = new JwtManager("SECRET_KEY");
        mapper = new PaginationCursorMapper(pojoMapper, objectMapper, jwtManager);
    }

    @Test
    void shouldMapPaginationCursorToLastEvaluatedKey() throws JsonProcessingException {
        //given
        String paginationCursor = getPaginationCursor();

        //when
        Map<String, AttributeValue> actual = mapper.mapFromCursor(paginationCursor);

        //then
        Assertions.assertEquals(getLastEvaluatedKey(), actual);
    }

    @Test
    void shouldMapLastEvaluatedKeyToPaginationCursor() throws JsonProcessingException {
        //given
        Map<String, AttributeValue> lastEvaluatedKey = getLastEvaluatedKey();

        //when
        String actual = mapper.mapToCursor(lastEvaluatedKey);

        //then
        Assertions.assertEquals(getPaginationCursor(), actual);
    }

    private String getPaginationCursor() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJjdXJzb3IiOiJ7XCJ5ZWFyXCI6XCJ7XFxcInNcXFwiOlxcXC" +
                "JCZW5ueVxcXCIsXFxcIm5cXFwiOlxcXCIxOTk5XFxcIixcXFwiYlxcXCI6bnVsbCxcXFwi" +
                "c3NcXFwiOm51bGwsXFxcIm5zXFxcIjpudWxsLFxcXCJic1xcXCI6bnVsbCxcXFwibVxcXCI" +
                "6bnVsbCxcXFwibFxcXCI6bnVsbCxcXFwiYm9vbFxcXCI6bnVsbCxcXFwibnVsXFxcIjpudW" +
                "xsfVwifSJ9.9DqZxzU8E79uft-AMT5FZYSNUURiLC6F70Us5f2bqR6C57t009uH6YLLEbkS" +
                "QM5uTJdx7TP4pUAn66zZtTvQyQ";
    }

    private Map<String, AttributeValue> getLastEvaluatedKey() {
        return Map.of("year", AttributeValue.builder().n("1999").s("Benny").build());
    }
}