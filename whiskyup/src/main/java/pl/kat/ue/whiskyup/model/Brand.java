package pl.kat.ue.whiskyup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@DynamoDbBean
public class Brand {

    public final static String PK = "BRANDS";
    public final static String SK = "BRANDS";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute("Brands")}))
    private Set<String> brands;

}