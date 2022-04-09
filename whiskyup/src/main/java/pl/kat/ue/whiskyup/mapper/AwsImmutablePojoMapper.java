package pl.kat.ue.whiskyup.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.SdkBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

@Component
@RequiredArgsConstructor
public class AwsImmutablePojoMapper {

    private final ObjectMapper objectMapper;

    public <B extends SdkBuilder<B, T>, T> T deserialize(String serialized, Class<? extends B> serializableBuilderClass) {
        try {
            return objectMapper.readValue(serialized, serializableBuilderClass).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <B extends CopyableBuilder<B, T>, T extends ToCopyableBuilder<B, T>> String serialize(T deserialized) {
        try {
            return objectMapper.writeValueAsString(deserialized.toBuilder());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}