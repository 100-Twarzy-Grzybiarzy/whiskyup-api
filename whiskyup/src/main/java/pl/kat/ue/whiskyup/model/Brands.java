package pl.kat.ue.whiskyup.model;

import lombok.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamoDbBean
public class Brands {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(AttributeNames.PARTITION_KEY)}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute(AttributeNames.SORT_KEY)}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Brand.BRANDS)}))
    private Set<String> values;

    public void addBrand(String brand) {
        if (Objects.isNull(values)) {
            values = new HashSet<>();
        }
        values.add(brand);
    }
}