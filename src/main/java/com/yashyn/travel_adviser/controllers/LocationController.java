package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.entities.City;
import com.yashyn.travel_adviser.data.entities.Country;
import com.yashyn.travel_adviser.data.repositories.CityRepository;
import com.yashyn.travel_adviser.data.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCitiesByCountry(@RequestParam Long countryId) {
        return ResponseEntity.ok(cityRepository.findByCountry_Id(countryId));
    }
}
