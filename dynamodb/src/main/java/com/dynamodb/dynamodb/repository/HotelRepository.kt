package com.dynamodb.dynamodb.repository

import com.dynamodb.dynamodb.model.Hotel
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface HotelRepository : CrudRepository<Hotel, String>, CustomHotelRepository {
    fun findAllByName(name: String): List<Hotel>
}