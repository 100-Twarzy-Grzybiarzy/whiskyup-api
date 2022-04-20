package pl.kat.ue.whiskyup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@DynamoDbBean
public class User {

    public final static String PK_PREFIX = "USER#";

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPartitionKey(){ return id; }
    public void setPartitionKey(String id){ this.id = id; }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSortKey(){ return id; }
    public void setSortKey(String id){ this.id = id; }

    @Getter
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute("Email")}))
    private String email;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("Distillery")}))
    private String distillery;
}
