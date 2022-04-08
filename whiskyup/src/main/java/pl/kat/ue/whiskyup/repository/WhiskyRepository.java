package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.model.Whisky;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

@Repository
public class WhiskyRepository {

    private final DynamoDbTable<Whisky> whiskyTable;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whisky:}") String name,
                            DynamoDbEnhancedClient dynamoDbClient) {

        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(Whisky.class));
    }

    public Page<Whisky> getAllWhiskies(final String exclusiveStartKey) {
        return whiskyTable.scan().iterator().next();
    }

}