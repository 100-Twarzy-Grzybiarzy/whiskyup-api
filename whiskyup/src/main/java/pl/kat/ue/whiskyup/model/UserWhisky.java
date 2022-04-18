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
public class UserWhisky {

    public final static String SK_PREFIX = "WHISKY#";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String whiskyId;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("ThumbnailUrl")}))
    private String thumbnailUrl;

    @Getter(onMethod = @__({@DynamoDbAttribute("Category")}))
    private String category;

    @Getter(onMethod = @__({@DynamoDbAttribute("Distillery")}))
    private String distillery;

    @Getter(onMethod = @__({@DynamoDbAttribute("Bottler")}))
    private String bottler;

    @Getter(onMethod = @__({@DynamoDbAttribute("BottlingSeries")}))
    private String bottlingSeries;

    @Getter(onMethod = @__({@DynamoDbAttribute("Bottled")}))
    private String bottled;

    @Getter(onMethod = @__({@DynamoDbAttribute("Vintage")}))
    private Integer vintage;

    @Getter(onMethod = @__({@DynamoDbAttribute("StagedAge")}))
    private Integer stagedAge;

    @Getter(onMethod = @__({@DynamoDbAttribute("Strength")}))
    private Double strength;

    @Getter(onMethod = @__({@DynamoDbAttribute("Size")}))
    private Integer size;

    @Getter(onMethod = @__({@DynamoDbAttribute("Rating")}))
    private Double rating;

    @Getter(onMethod = @__({@DynamoDbAttribute("UserRating")}))
    private Double userRating;

    @Getter(onMethod = @__({@DynamoDbAttribute("AmountOfRatings")}))
    private Integer amountOfRatings;

    @Getter(onMethod = @__({@DynamoDbAttribute("Price")}))
    private String price;

    @Getter(onMethod = @__({@DynamoDbAttribute("Tags")}))
    private Set<String> tags;
}
