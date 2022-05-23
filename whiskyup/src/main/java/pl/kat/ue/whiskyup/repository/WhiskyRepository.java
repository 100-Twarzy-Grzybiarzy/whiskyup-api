package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.dynamometadata.Expressions;
import pl.kat.ue.whiskyup.dynamometadata.IndexNames;
import pl.kat.ue.whiskyup.model.Brands;
import pl.kat.ue.whiskyup.model.SortTypeDto;
import pl.kat.ue.whiskyup.model.Whisky;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.kat.ue.whiskyup.service.WhiskyService.PAGE_LIMIT;
import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Repository
public class WhiskyRepository {

    private final BrandRepository brandRepository;
    private final DynamoDbTable<Whisky> whiskyTable;
    private final DynamoDbIndex<Whisky> gsi1Index;
    private final DynamoDbIndex<Whisky> gsi2Index;
    private final DynamoDbIndex<Whisky> gsi3Index;
    private final DynamoDbIndex<Whisky> gsi4Index;
    private final DynamoDbEnhancedClient dynamoClient;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                            DynamoDbEnhancedClient dynamoDbClient,
                            BrandRepository brandRepository) {

        this.dynamoClient = dynamoDbClient;
        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(Whisky.class));
        this.brandRepository = brandRepository;
        this.gsi1Index = whiskyTable.index(IndexNames.GSI_1);
        this.gsi2Index = whiskyTable.index(IndexNames.GSI_2);
        this.gsi3Index = whiskyTable.index(IndexNames.GSI_3);
        this.gsi4Index = whiskyTable.index(IndexNames.GSI_4);
    }

    public void addWhisky(Whisky whisky) {
        dynamoClient.transactWriteItems(TransactWriteItemsEnhancedRequest.builder()
                .addUpdateItem(brandRepository.brandTable, updateBrandsRequest(whisky.getBrand()))
                .addPutItem(whiskyTable, putWhiskyBaseRequest(whisky))
                .build()
        );
    }

    private TransactUpdateItemEnhancedRequest<Brands> updateBrandsRequest(String brand) {
        Brands brands = brandRepository.getBrands();
        brands.addBrand(parseBrand(brand));
        return TransactUpdateItemEnhancedRequest.builder(Brands.class)
                .item(brands)
                .conditionExpression(Expression.builder()
                        .expression(Expressions.ATTRIBUTE_EXISTS)
                        .build())
                .build();
    }

    private String parseBrand(String brand) {
        if (brand != null && brand.length() > 2) {
            brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
        }
        return brand;
    }

    private TransactPutItemEnhancedRequest<Whisky> putWhiskyBaseRequest(Whisky whisky) {
        return TransactPutItemEnhancedRequest.builder(Whisky.class)
                .item(whisky)
                .conditionExpression(Expression.builder()
                        .expression(Expressions.ATTRIBUTE_NOT_EXISTS)
                        .build())
                .build();
    }

    public Page<Whisky> getWhiskies(Map<String, AttributeValue> exclusiveStartKey, LocalDate lastSeenDate, int limit) {
        QueryConditional queryWhiskies = keyEqualTo(Key.builder()
                .partitionValue(AttributeValues.Whisky.GSI1_PARTITION_KEY + lastSeenDate)
                .build());

        return gsi1Index.query(q -> q.queryConditional(queryWhiskies)
                        .limit(limit)
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public Page<Whisky> getWhiskiesByBrand(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(AttributeValues.Whisky.GSI3_PARTITION_KEY + searchDto.getValue().toLowerCase())
                .build());

        return gsi3Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .limit(PAGE_LIMIT)
                        .scanIndexForward(shouldScanForward(searchDto))
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    private static boolean shouldScanForward(SearchWhiskiesDto searchDto) {
        SortTypeDto sortType = searchDto.getSort();
        if (SortTypeDto.DESC.equals(sortType)) {
            return false;
        } else if (SortTypeDto.ASC.equals(sortType)) {
            return true;
        } else {
            return true;
        }
    }

    public Page<Whisky> getWhiskiesByPriceRange(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(AttributeValues.Whisky.GSI2_PARTITION_KEY + searchDto.getValue().toLowerCase())
                .build());

        return gsi2Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .limit(PAGE_LIMIT)
                        .scanIndexForward(shouldScanForward(searchDto))
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public Page<Whisky> getWhiskiesUrls(Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(AttributeValues.Whisky.GSI4_PARTITION_KEY)
                .build());

        return gsi4Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .exclusiveStartKey(exclusiveStartKey)
                        .attributesToProject(AttributeNames.Whisky.URL)
                        .scanIndexForward(false))
                .iterator()
                .next();
    }

    public Whisky getWhiskyByUrl(String url) {
        QueryConditional queryWhiskyByUrl = keyEqualTo(Key.builder()
                .partitionValue(AttributeValues.Whisky.GSI4_PARTITION_KEY)
                .sortValue(AttributeValues.Whisky.GSI4_SORT_KEY + url)
                .build());

        List<Whisky> whisky = gsi4Index.query(q -> q.queryConditional(queryWhiskyByUrl)
                        .attributesToProject(AttributeNames.Whisky.ID))
                .iterator()
                .next()
                .items();

        return !whisky.isEmpty() ? whisky.get(0) : null;
    }

    public boolean deleteWhisky(String whiskyUrl) {
        return Optional.ofNullable(getWhiskyByUrl(whiskyUrl))
                .map(whiskyToDelete -> {
                    String id = whiskyToDelete.getId();
                    Key key = Key.builder()
                            .partitionValue(AttributeValues.Whisky.PARTITION_KEY + id)
                            .sortValue(AttributeValues.Whisky.SORT_KEY + id)
                            .build();
                    whiskyTable.deleteItem(key);
                    return true;
                })
                .orElse(false);
    }
}