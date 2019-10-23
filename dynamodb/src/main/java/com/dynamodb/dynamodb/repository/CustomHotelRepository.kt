package com.dynamodb.dynamodb.repository

import java.io.IOException


interface CustomHotelRepository {
    fun createTable()
    @Throws(IOException::class)
    fun loadData()
}