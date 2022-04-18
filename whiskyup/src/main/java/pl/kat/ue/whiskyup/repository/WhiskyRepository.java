package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.model.Whisky;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Repository
public class WhiskyRepository {

    private final DynamoDbTable<Whisky> whiskyTable;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whisky-base}") String name,
                            DynamoDbEnhancedClient dynamoDbClient) {

        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(Whisky.class));
    }

    public Page<Whisky> getAllWhiskies(Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyTable.scan(r -> r.exclusiveStartKey(exclusiveStartKey).limit(3))
                .iterator()
                .next();
    }

}