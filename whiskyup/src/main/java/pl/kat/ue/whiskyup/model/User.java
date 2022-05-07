package pl.kat.ue.whiskyup.model;

import lombok.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamoDbBean
public class User {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(AttributeNames.PARTITION_KEY)}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute(AttributeNames.SORT_KEY)}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.User.ID)}))
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.User.EMAIL)}))
    private String email;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.User.NAME)}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.User.DISTILLERY)}))
    private String distillery;
}