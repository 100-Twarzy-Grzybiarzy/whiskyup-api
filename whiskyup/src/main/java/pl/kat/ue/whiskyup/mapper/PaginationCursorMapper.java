package pl.kat.ue.whiskyup.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.utils.JwtManager;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaginationCursorMapper {

    private final AwsImmutablePojoMapper pojoMapper;
    private final ObjectMapper objectMapper;
    private final JwtManager jwtManager;

    public Map<String, AttributeValue> mapFromCursor(String paginationCursor) throws JsonProcessingException {
        String decoded = jwtManager.parseJwt(paginationCursor);
        Map<String, String> lastEvaluatedKey = objectMapper.readValue(decoded, new TypeReference<>() {});
        return deserialize(lastEvaluatedKey);
    }

    private Map<String, AttributeValue> deserialize(Map<String, String> lastEvaluatedKey) {
        return lastEvaluatedKey.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> pojoMapper.deserialize(e.getValue(), AttributeValue.serializableBuilderClass())));
    }

    public String mapToCursor(Map<String, AttributeValue> lastEvaluatedKey) throws JsonProcessingException {
        Map<String, String> serialized = serialize(lastEvaluatedKey);
        String paginationCursor = objectMapper.writeValueAsString(serialized);
        return jwtManager.buildJwt(paginationCursor);
    }

    private Map<String, String> serialize(Map<String, AttributeValue> lastEvaluatedKey) {
        return lastEvaluatedKey.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> pojoMapper.serialize(e.getValue())));
    }
}