package pl.kat.ue.whiskyup.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
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

    @Value("${cloud.aws.credentials.access-key:}")
    private String AWS_ACCESS_KEY;

    @Value("${cloud.aws.credentials.secret-key:}")
    private String AWS_SECRET_KEY;


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

    @Bean("amazonSQS")
    @Profile("default")
    public AmazonSQSAsync getSqsClient() {
        return AmazonSQSAsyncClientBuilder.standard().build();
    }

    @Bean("amazonSQS")
    @Profile("develop")
    public AmazonSQSAsync getSqsClientDevelop() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(AWS_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)))
                .build();
    }

    @Bean("amazonSQS")
    @Profile("local")
    public AmazonSQSAsync getSqsClientLocal() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(LOCALSTACK_ENDPOINT, AWS_REGION))
                .build();
    }

    @Bean
    protected MessageConverter messageConverter() {
        var converter = new MappingJackson2MessageConverter();
        converter.setSerializedPayloadClass(String.class);
        converter.setStrictContentTypeMatch(false);
        return converter;
    }

}