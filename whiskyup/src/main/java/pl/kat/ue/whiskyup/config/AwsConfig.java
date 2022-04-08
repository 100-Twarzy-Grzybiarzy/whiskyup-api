package pl.kat.ue.whiskyup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region.static:eu-central-1}")
    private String AWS_REGION;

    @Value("${cloud.aws.end-point.uri:}")
    private String LOCALSTACK_ENDPOINT;

    @Bean
    public AwsCredentialsProvider getAwsCredentialsProvider() {
        return DefaultCredentialsProvider.builder()
                .build();
    }

    @Bean("dynamoDbClient")
    @Profile("!local")
    public DynamoDbClient getDynamoDbClient(AwsCredentialsProvider provider) {
        return DynamoDbClient.builder()
                .region(Region.of(AWS_REGION))
                .credentialsProvider(provider)
                .build();
    }

    @Bean("dynamoDbClient")
    @Profile("local")
    public DynamoDbClient getDynamoDbClientLocal(AwsCredentialsProvider provider) {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(LOCALSTACK_ENDPOINT))
                .region(Region.of(AWS_REGION))
                .credentialsProvider(provider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}