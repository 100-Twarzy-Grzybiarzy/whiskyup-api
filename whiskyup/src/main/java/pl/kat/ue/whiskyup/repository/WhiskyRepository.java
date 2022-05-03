package pl.kat.ue.whiskyup.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.model.Brands;
import pl.kat.ue.whiskyup.model.SortTypeDto;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.Map;

import static pl.kat.ue.whiskyup.model.WhiskyBase.*;
import static pl.kat.ue.whiskyup.service.WhiskyService.PAGE_LIMIT;
import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Repository
public class WhiskyRepository {

    private final BrandRepository brandRepository;
    private final DynamoDbTable<WhiskyBase> whiskyTable;
    private final DynamoDbIndex<WhiskyBase> gsi1Index;
    private final DynamoDbIndex<WhiskyBase> gsi2Index;
    private final DynamoDbIndex<WhiskyBase> gsi3Index;
    private final DynamoDbEnhancedClient dynamoClient;

    public WhiskyRepository(@Value("${cloud.aws.dynamodb.table.whiskyup}") String name,
                            DynamoDbEnhancedClient dynamoDbClient,
                            BrandRepository brandRepository) {

        this.dynamoClient = dynamoDbClient;
        this.whiskyTable = dynamoDbClient.table(name, TableSchema.fromBean(WhiskyBase.class));
        this.brandRepository = brandRepository;
        this.gsi1Index = whiskyTable.index("GSI1");
        this.gsi2Index = whiskyTable.index("GSI2");
        this.gsi3Index = whiskyTable.index("GSI3");
    }

    public void addWhisky(WhiskyBase whiskyBase) {
        dynamoClient.transactWriteItems(TransactWriteItemsEnhancedRequest.builder()
                .addUpdateItem(brandRepository.brandTable, updateBrandsRequest(whiskyBase.getBrand()))
                .addPutItem(whiskyTable, putWhiskyBaseRequest(whiskyBase))
                .build()
        );
    }

    private TransactUpdateItemEnhancedRequest<Brands> updateBrandsRequest(String brand) {
        Brands brands = brandRepository.getBrands();
        brands.addBrand(parseBrand(brand));
        return TransactUpdateItemEnhancedRequest.builder(Brands.class)
                .item(brands)
                .conditionExpression(Expression.builder()
                        .expression("attribute_exists(PK)")
                        .build())
                .build();
    }

    private String parseBrand(String brand) {
        if (brand != null && brand.length() > 2) {
            brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
        }
        return brand;
    }

    private TransactPutItemEnhancedRequest<WhiskyBase> putWhiskyBaseRequest(WhiskyBase whiskyBase) {
        return TransactPutItemEnhancedRequest.builder(WhiskyBase.class)
                .item(whiskyBase)
                .conditionExpression(Expression.builder()
                        .expression("attribute_not_exists(PK)")
                        .build())
                .build();
    }

    public Page<WhiskyBase> getWhiskies(Map<String, AttributeValue> exclusiveStartKey, LocalDate lastSeenDate, int limit) {
        QueryConditional queryWhiskies = keyEqualTo(Key.builder()
                .partitionValue(GSI1_PK_PREFIX + lastSeenDate)
                .build());

        return gsi1Index.query(q -> q.queryConditional(queryWhiskies)
                        .limit(limit)
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }

    public Page<WhiskyBase> getWhiskiesByBrand(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(GSI3_PK_PREFIX + searchDto.getValue().toLowerCase())
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

    public Page<WhiskyBase> getWhiskiesByPriceRange(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        QueryConditional queryWhiskiesByBrand = keyEqualTo(Key.builder()
                .partitionValue(GSI2_PK_PREFIX + searchDto.getValue().toLowerCase())
                .build());

        return gsi2Index.query(q -> q.queryConditional(queryWhiskiesByBrand)
                        .limit(PAGE_LIMIT)
                        .scanIndexForward(shouldScanForward(searchDto))
                        .exclusiveStartKey(exclusiveStartKey))
                .iterator()
                .next();
    }
}