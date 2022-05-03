package pl.kat.ue.whiskyup.model;

import lombok.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import pl.kat.ue.whiskyup.dynamometadata.IndexNames;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamoDbBean
public class Whisky {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(AttributeNames.PARTITION_KEY)}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute(AttributeNames.SORT_KEY)}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = IndexNames.GSI_1), @DynamoDbAttribute(AttributeNames.GSI1_PARTITION_KEY)}))
    private String gsi1pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = IndexNames.GSI_1), @DynamoDbAttribute(AttributeNames.GSI1_SORT_KEY)}))
    private String gsi1sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = IndexNames.GSI_2), @DynamoDbAttribute(AttributeNames.GSI2_PARTITION_KEY)}))
    private String gsi2pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = IndexNames.GSI_2), @DynamoDbAttribute(AttributeNames.GSI2_SORT_KEY)}))
    private String gsi2sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = IndexNames.GSI_3), @DynamoDbAttribute(AttributeNames.GSI3_PARTITION_KEY)}))
    private String gsi3pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = IndexNames.GSI_3), @DynamoDbAttribute(AttributeNames.GSI3_SORT_KEY)}))
    private String gsi3sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = IndexNames.GSI_4), @DynamoDbAttribute(AttributeNames.GSI4_PARTITION_KEY)}))
    private String gsi4pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = IndexNames.GSI_4), @DynamoDbAttribute(AttributeNames.GSI4_SORT_KEY)}))
    private String gsi4sk;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.ID)}))
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.URL)}))
    private String url;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.ADDED_DATE)}))
    private LocalDate addedDate;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.NAME)}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.THUMBNAIL_URL)}))
    private String thumbnailUrl;

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

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.BRAND)}))
    private String brand;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.VINTAGE)}))
    private Integer vintage;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.STAGED_AGE)}))
    private Integer stagedAge;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.STRENGTH)}))
    private Double strength;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.SIZE)}))
    private Integer size;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.RATING)}))
    private Double rating;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.USER_RATING)}))
    private Double userRating;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.AMOUNT_OF_RATINGS)}))
    private Integer amountOfRatings;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.PRICE)}))
    private Double price;

    @Getter(onMethod = @__({@DynamoDbAttribute(AttributeNames.Whisky.TAGS)}))
    private Set<String> tags;
}