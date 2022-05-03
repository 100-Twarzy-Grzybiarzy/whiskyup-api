package pl.kat.ue.whiskyup.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@DynamoDbBean
public class WhiskyBase {

    public final static String PK_PREFIX = "WHISKY#";
    public final static String SK_PREFIX = "WHISKY#";
    public final static String GSI1_PK_PREFIX = "WHISKIES#";
    public final static String GSI1_SK_PREFIX = "WHISKY#";
    public final static String GSI2_PK_PREFIX = "PRICERANGE#";
    public final static String GSI2_SK_PREFIX = "PRICE#%.2f#WHISKY#";
    public final static String GSI3_PK_PREFIX = "BRAND#";
    public final static String GSI3_SK_PREFIX = "WHISKY#";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = "GSI1"), @DynamoDbAttribute("GSI1PK")}))
    private String gsi1pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = "GSI1"), @DynamoDbAttribute("GSI1SK")}))
    private String gsi1sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = "GSI2"), @DynamoDbAttribute("GSI2PK")}))
    private String gsi2pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = "GSI2"), @DynamoDbAttribute("GSI2SK")}))
    private String gsi2sk;

    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(indexNames = "GSI3"), @DynamoDbAttribute("GSI3PK")}))
    private String gsi3pk;

    @Getter(onMethod = @__({@DynamoDbSecondarySortKey(indexNames = "GSI3"), @DynamoDbAttribute("GSI3SK")}))
    private String gsi3sk;

    @Getter(onMethod = @__({@DynamoDbAttribute("Id")}))
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute("Url")}))
    private String url;

    @Getter(onMethod = @__({@DynamoDbAttribute("AddedDate")}))
    private LocalDate addedDate;

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

    @Getter(onMethod = @__({@DynamoDbAttribute("Brand")}))
    private String brand;

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
    private Double price;

    @Getter(onMethod = @__({@DynamoDbAttribute("Tags")}))
    private Set<String> tags;

}