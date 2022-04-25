package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.model.Whisky;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
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
        return whiskyTable.scan(r -> r.exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public void addWhisky(Whisky whisky) {
        whiskyTable.putItem(putWhiskyRequest(whisky));
    }

    private PutItemEnhancedRequest<Whisky> putWhiskyRequest(Whisky whisky) {
        return PutItemEnhancedRequest.builder(Whisky.class)
                .item(whisky)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(PK)")
                        .build())
                .build();
    }

}