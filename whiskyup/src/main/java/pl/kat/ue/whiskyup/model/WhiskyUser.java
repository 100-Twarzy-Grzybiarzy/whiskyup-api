package pl.kat.ue.whiskyup.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@DynamoDbBean
public class WhiskyUser {

    public final static String PK_PREFIX = "USER#";
    public final static String SK_PREFIX = "WHISKY#";

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("PK")}))
    private String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("SK")}))
    private String sk;

    @Getter(onMethod = @__({@DynamoDbAttribute("UserId")}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbAttribute("WhiskyId")}))
    private String whiskyId;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

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

    @Getter(onMethod = @__({@DynamoDbAttribute("Price")}))
    private Double price;

    @Getter(onMethod = @__({@DynamoDbAttribute("Tags")}))
    private Set<String> tags;

}