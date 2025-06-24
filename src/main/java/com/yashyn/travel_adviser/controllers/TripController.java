package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.dto.CreateTripRequest;
import com.yashyn.travel_adviser.data.dto.TripDto;
import com.yashyn.travel_adviser.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripDto> createTrip(@RequestBody CreateTripRequest tripCommand,
                                          Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripService.createTrip(tripCommand, principal.getName()));
    }

    @GetMapping
    public List<TripDto> listAllTrips(Principal principal) {
        return tripService.getUserTrips(principal.getName());
    }

    @GetMapping("/{id}")
    public TripDto tripDetails(@PathVariable Long id, Principal principal) {
        return tripService.getTripDetails(id, principal.getName());
    }

    @PostMapping("/{id}/complete")
    public void markTripCompleted(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        tripService.markTripAsCompleted(id, userDetails.getUsername());
    }

    @PostMapping("/{id}/advice")
    public String generateAdvice(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return tripService.generateAdvice(id, userDetails.getUsername());
    }
}

