package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.Map;

import static pl.kat.ue.whiskyup.model.WhiskyBase.*;
import static pl.kat.ue.whiskyup.service.WhiskyService.PAGE_LIMIT;
import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Repository
public class WhiskyRepository {

    private final DynamoDbTable<WhiskyBase> whiskyTable;
    private final DynamoDbIndex<WhiskyBase> gsi1Index;
    private final DynamoDbIndex<WhiskyBase> gsi2Index;
    private final DynamoDbIndex<WhiskyBase> gsi3Index;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                            DynamoDbEnhancedClient dynamoDbClient) {

        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(WhiskyBase.class));
        this.gsi1Index = whiskyTable.index("GSI1");
        this.gsi2Index = whiskyTable.index("GSI2");
        this.gsi3Index = whiskyTable.index("GSI3");
    }

    public Page<WhiskyBase> getWhiskies(Map<String, AttributeValue> exclusiveStartKey, LocalDate lastSeenDate, int limit) {
        QueryConditional queryWhiskies = keyEqualTo(Key.builder()
                .partitionValue(GSI1_PK_PREFIX + lastSeenDate)
                .build());

        return gsi1Index.query(q -> q.queryConditional(queryWhiskies)
                        .limit(limit)
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public void addWhisky(WhiskyBase whiskyBase) {
        PutItemEnhancedRequest<WhiskyBase> putWhiskyRequest = PutItemEnhancedRequest.builder(WhiskyBase.class)
                .item(whiskyBase)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(PK)")
                        .build())
                .build();

        whiskyTable.putItem(putWhiskyRequest);
    }

    public Page<WhiskyBase> getWhiskiesByBrand(String brand, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(GSI3_PK_PREFIX + brand.toLowerCase())
                .build());

        return gsi3Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .limit(PAGE_LIMIT)
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public Page<WhiskyBase> getWhiskiesByPriceRange(String priceRange, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(GSI2_PK_PREFIX + priceRange)
                .build());

        return gsi2Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .limit(PAGE_LIMIT)
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }
}