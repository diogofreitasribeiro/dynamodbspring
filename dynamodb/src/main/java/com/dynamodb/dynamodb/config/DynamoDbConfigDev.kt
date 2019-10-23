package com.dynamodb.dynamodb.config

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("dev")
@Configuration
@EnableDynamoDBRepositories(basePackages = ["org.smartinrubio.springbootdynamodb.repository"])
class DynamoDBConfigDev {

    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String? = null

    @Value("\${amazon.dynamodb.region}")
    private val amazonDynamoDBRegion: String? = null

    @Bean
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig {
        return DynamoDBMapperConfig.DEFAULT
    }

    @Bean
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB, config: DynamoDBMapperConfig): DynamoDBMapper {
        return DynamoDBMapper(amazonDynamoDB, config)
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion))
                .build()
    }

    @Bean
    fun dynamoDB(): DynamoDB {
        return DynamoDB(amazonDynamoDB())
    }
}