package pl.kat.ue.whiskyup.model;

import lombok.*;
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

    public final static String PK_PREFIX = "USER#";
    public final static String SK_PREFIX = "USER#";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute("Id")}))
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute("Email")}))
    private String email;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("Distillery")}))
    private String distillery;

}