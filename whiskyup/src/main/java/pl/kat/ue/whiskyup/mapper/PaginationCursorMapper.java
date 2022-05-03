package pl.kat.ue.whiskyup.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.utils.JwtManager;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaginationCursorMapper {

    private final AwsImmutablePojoMapper pojoMapper;
    private final ObjectMapper objectMapper;
    private final JwtManager jwtManager;

    public Map<String, AttributeValue> mapFromCursor(String paginationCursor) {
        if (Objects.isNull(paginationCursor)) {
            return null;
        } try {
            String decoded = jwtManager.parseJwt(paginationCursor);
            Map<String, String> exclusiveLastKey = objectMapper.readValue(decoded, new TypeReference<>() {});
            return deserialize(exclusiveLastKey);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, AttributeValue> deserialize(Map<String, String> exclusiveLastKey) {
        return exclusiveLastKey.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> pojoMapper.deserialize(e.getValue(), AttributeValue.serializableBuilderClass()))
                );
    }

    public String mapToCursor(Map<String, AttributeValue> lastEvaluatedKey) {
        if (Objects.isNull(lastEvaluatedKey)) {
            return null;
        } try {
            Map<String, String> serialized = serialize(lastEvaluatedKey);
            String paginationCursor = objectMapper.writeValueAsString(serialized);
            return jwtManager.buildJwt(paginationCursor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> serialize(Map<String, AttributeValue> lastEvaluatedKey) {
        return lastEvaluatedKey.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> pojoMapper.serialize(e.getValue()))
                );
    }
}