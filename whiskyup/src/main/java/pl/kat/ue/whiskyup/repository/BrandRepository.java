package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.model.Brands;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.HashSet;
import java.util.Optional;

@Repository
public class BrandRepository {

    public final DynamoDbTable<Brands> brandTable;

    public BrandRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                           DynamoDbEnhancedClient dynamoDbClient) {

        this.brandTable = dynamoDbClient.table(name, TableSchema.fromBean(Brands.class));
    }

    public Brands getBrands() {
        Key getBrandsKey = Key.builder()
                .partitionValue(AttributeValues.Brand.PARTITION_KEY)
                .sortValue(AttributeValues.Brand.SORT_KEY)
                .build();

        return Optional.ofNullable(brandTable.getItem(getBrandsKey))
                .orElseGet(() -> {
                    Brands brands = Brands.builder()
                            .pk(AttributeValues.Brand.PARTITION_KEY)
                            .sk(AttributeValues.Brand.SORT_KEY)
                            .build();
                    brandTable.putItem(brands);
                    brands.setValues(new HashSet<>());
                    return brands;
                });
    }
}