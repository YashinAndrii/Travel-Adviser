package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.dto.CreateTripCommand;
import com.yashyn.travel_adviser.data.dto.TripDto;
import com.yashyn.travel_adviser.data.repositories.CountryRepository;
import com.yashyn.travel_adviser.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final CountryRepository countryRepository;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        return "create-trip";
    }

    @PostMapping
    public ResponseEntity<TripDto> createTrip(@RequestBody CreateTripCommand cmd,
                                          Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripService.createTrip(cmd, principal.getName()));
    }

    @GetMapping
    public List<TripDto> listAllTrips(Principal principal) {
        return tripService.getUserTrips(principal.getName());
    }

    @GetMapping("/{id}")
    public TripDto tripDetails(@PathVariable Long id, Principal principal) {
        return tripService.getTripDetails(id, principal.getName());
    }

    /*@PostMapping
    public String createTripFromForm(@RequestParam String countries,
                                     @RequestParam String cities,
                                     @RequestParam TripType type,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                     @RequestParam BigDecimal budget,
                                     @RequestParam(required = false) boolean includeTransport,
                                     @RequestParam(required = false) boolean hasChildren,
                                     @RequestParam(required = false) String additionalInfo,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        List<Long> countryIds = Arrays.stream(countries.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        List<String> countryNames = countryRepository.findAllById(countryIds).stream()
                .map(Country::getName)
                .toList();

        tripService.createTrip(
                userDetails.getUsername(),
                countryNames,
                Arrays.stream(cities.split(",")).map(String::trim).toList(),
                type,
                startDate,
                endDate,
                budget,
                includeTransport,
                hasChildren,
                additionalInfo
        );

        return "redirect:/trips/my";
    }*/

/*    @GetMapping("/my")
    public String getMyTripsPage(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        List<Trip> trips = tripService.getUserTrips(userDetails.getUsername());
        model.addAttribute("trips", trips);

        return "my-trips";
    }*/

    @PostMapping("/{id}/complete")
    public String markTripCompleted(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        tripService.markTripAsCompleted(id, userDetails.getUsername());
        return "redirect:/trips/my";
    }

    @PostMapping("/{id}/advice")
    public String generateAdvice(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        tripService.generateAdvice(id, userDetails.getUsername());
        return "redirect:/trips/" + id;
    }

/*    @GetMapping("/{id}")
    public String viewTrip(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        Trip trip = tripService.getTripDetails(id, userDetails.getUsername());
        model.addAttribute("trip", trip);
        return "trip-details";
    }*/

}

