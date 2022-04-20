package pl.kat.ue.whiskyup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region.static:}")
    private String AWS_REGION;

    @Value("${cloud.aws.endpoint:}")
    private String LOCALSTACK_ENDPOINT;

    @Bean("dynamoDbClient")
    @Profile("default")
    public DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.create();
    }

    @Bean("dynamoDbClient")
    @Profile("develop")
    public DynamoDbClient getDynamoDbClientDevelop() {
        return DynamoDbClient.builder()
                .region(Region.of(AWS_REGION))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }

    @Bean("dynamoDbClient")
    @Profile("local")
    public DynamoDbClient getDynamoDbClientLocal() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(LOCALSTACK_ENDPOINT))
                .region(Region.of(AWS_REGION))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}