package pl.kat.ue.whiskyup.model;

import lombok.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamoDbBean
public class UserWhisky {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(AttributeNames.PARTITION_KEY)}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute(AttributeNames.SORT_KEY)}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.UserWhisky.USER_ID)}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.UserWhisky.WHISKY_ID)}))
    private String whiskyId;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.NAME)}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.CATEGORY)}))
    private String category;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.DISTILLERY)}))
    private String distillery;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.BOTTLER)}))
    private String bottler;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.BOTTLING_SERIES)}))
    private String bottlingSeries;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.BOTTLED)}))
    private String bottled;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.VINTAGE)}))
    private Integer vintage;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.STATED_AGE)}))
    private Integer statedAge;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.STRENGTH)}))
    private Double strength;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.SIZE)}))
    private Integer size;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.SIZE_UNIT)}))
    private String sizeUnit;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.RATING)}))
    private Double rating;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.USER_RATING)}))
    private Double userRating;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.PRICE)}))
    private Double price;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.CURRENCY)}))
    private String currency;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.TAGS)}))
    private Set<String> tags;
}