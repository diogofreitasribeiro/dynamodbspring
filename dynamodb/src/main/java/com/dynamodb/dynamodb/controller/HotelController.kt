package com.dynamodb.dynamodb.controller

import com.dynamodb.dynamodb.exception.HotelNotFoundException
import com.dynamodb.dynamodb.model.Hotel
import com.dynamodb.dynamodb.repository.HotelRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/hotels")
class HotelController(val repository: HotelRepository) {

    @GetMapping("/table")
    fun createTable(): ResponseEntity<String> {
        repository.createTable()
        return ResponseEntity.ok("Table Created!")
    }

    @GetMapping("/data")
    fun loadData(): ResponseEntity<String> {
        repository.loadData()
        return ResponseEntity.ok("Data Loaded")
    }

    @GetMapping
    fun readAll(): Iterable<Hotel> {
        return repository.findAll()
    }

    @PostMapping
    fun createHotel(@RequestBody hotel: Hotel, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Hotel> {
        var savedHotel: Hotel = repository.save(hotel)
        var headers = HttpHeaders()
        var locationUri = uriComponentsBuilder
                .path("/hotels/")
                .path(savedHotel.id)
                .build()
                .toUri()
        headers.setLocation(locationUri)

        return ResponseEntity(savedHotel, headers, HttpStatus.CREATED)
    }

    @GetMapping("/{hotelId}")
    fun readHotelById(@PathVariable("hotelId") id: String): Hotel {
        return repository.findById(id).orElseThrow{HotelNotFoundException()}
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateHotel(@RequestBody hotel: Hotel): Hotel {

        repository.findById(hotel.id).orElseThrow{HotelNotFoundException()}

        return repository.save(hotel)
    }

    @GetMapping
    fun readHotelByName(@RequestParam("hotelName") name: String): Iterable<Hotel> {
        return repository.findAllByName(name)
    }

    @DeleteMapping("/{hotelId}")
    fun deleteHotel(@PathVariable("hotelId") id: String) {
        repository.deleteById(id)
    }
}