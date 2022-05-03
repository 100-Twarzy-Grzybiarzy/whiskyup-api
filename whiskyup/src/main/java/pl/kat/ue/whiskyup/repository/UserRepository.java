package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.model.User;
import pl.kat.ue.whiskyup.model.UserWhisky;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Repository
public class UserRepository {

    private final DynamoDbTable<UserWhisky> userWhiskyTable;
    private final DynamoDbTable<User> userTable;

    public UserRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                          DynamoDbEnhancedClient dynamoDbClient) {

        this.userWhiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(UserWhisky.class));
        this.userTable = dynamoDbClient.table(name, TableSchema.fromBean(User.class));
    }

    public User addUser(User user) {
        userTable.putItem(user);
        return user;
    }

    public Page<UserWhisky> getAllUserWhiskies(String userId, Map<String, AttributeValue> exclusiveStartKey) {
        return userWhiskyTable.query(QueryEnhancedRequest.builder()
                        .exclusiveStartKey(exclusiveStartKey)
                        .queryConditional(QueryConditional.sortBeginsWith(
                                Key.builder()
                                        .partitionValue(AttributeValues.UserWhisky.PARTITION_KEY + userId)
                                        .sortValue(AttributeValues.UserWhisky.SORT_KEY)
                                        .build()))
                        .build())
                .iterator()
                .next();
    }

    public UserWhisky addUserWhisky(UserWhisky userWhisky) {
        userWhiskyTable.putItem(userWhisky);
        return userWhisky;
    }
}