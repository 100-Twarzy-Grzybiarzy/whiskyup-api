package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.model.WhiskyBase;
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

    private final DynamoDbTable<WhiskyBase> whiskyTable;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                            DynamoDbEnhancedClient dynamoDbClient) {

        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(WhiskyBase.class));
    }

    public Page<WhiskyBase> getAllWhiskies(Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyTable.scan(r -> r.exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public void addWhisky(WhiskyBase whiskyBase) {
        whiskyTable.putItem(putWhiskyRequest(whiskyBase));
    }

    private PutItemEnhancedRequest<WhiskyBase> putWhiskyRequest(WhiskyBase whiskyBase) {
        return PutItemEnhancedRequest.builder(WhiskyBase.class)
                .item(whiskyBase)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(PK)")
                        .build())
                .build();
    }

}