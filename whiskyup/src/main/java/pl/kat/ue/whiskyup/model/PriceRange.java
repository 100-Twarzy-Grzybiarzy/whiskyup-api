package pl.kat.ue.whiskyup.model;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Set;

@Setter
@DynamoDbBean
public class PriceRange {

    public final static String PK = "PRICERANGES";
    public final static String SK = "PRICERANGES";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute("PriceRanges")}))
    private Set<String> priceRanges;

}