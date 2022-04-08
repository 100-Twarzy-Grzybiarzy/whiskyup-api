package pl.kat.ue.whiskyup.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Whisky {

    private String url;
    private String name;
    private String thumbnailUrl;
    private String category;
    private String distillery;
    private String bottler;
    private String bottlerSeries;
    private String bottled;
    private Integer vintage;
    private Integer statedAge;
    private Double strength;
    private Integer size;
    private Double rating;
    private Double userRating;
    private Integer amountOfRatings;
    private String price;
    private Set<String> tags;

    @DynamoDbPartitionKey
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}