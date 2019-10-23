package com.dynamodb.dynamodb.model

import com.amazonaws.services.dynamodbv2.datamodeling.*
import lombok.*

@Data
@DynamoDBTable(tableName = "Hotels")
class Hotel {

    @DynamoDBHashKey
    @DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE) // Requires a mutable object
    val id: String? = null

    @DynamoDBAttribute
    val name: String? = null

}