package com.dynamodb.dynamodb.repository

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.model.*
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import lombok.extern.slf4j.Slf4j
import java.io.File
import java.io.IOException

@Slf4j
class CustomHotelRepositoryImpl(private val dynamoDB: DynamoDB) : CustomHotelRepository {

    override fun createTable() {

        try {
            val table = dynamoDB.createTable(TABLE_NAME,
                    listOf(KeySchemaElement("id", KeyType.HASH)),
                    listOf(AttributeDefinition("id", ScalarAttributeType.S)),
                    ProvisionedThroughput(10L, 10L))
            table.waitForActive()

        } catch (e: ResourceInUseException) {


        } catch (e: Exception) {

        }

    }

    @Throws(IOException::class)
    override fun loadData() {
        val table = dynamoDB.getTable(TABLE_NAME)

        val parser = JsonFactory().createParser(File("hotels.json"))

        val rootNode = ObjectMapper().readTree<JsonNode>(parser)
        val iterator = rootNode.iterator()

        var currentNode: ObjectNode

        while (iterator.hasNext()) {
            currentNode = iterator.next() as ObjectNode

            val id = currentNode.path("id").asText()
            val name = currentNode.path("name").asText()

            try {
                table.putItem(Item()
                        .withPrimaryKey("id", id)
                        .withString("name", name))

            } catch (e: Exception) {
                break
            }

        }
        parser.close()
    }

    companion object {

        private val TABLE_NAME = "Hotels"
    }
}