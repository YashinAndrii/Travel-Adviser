package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.CreateTripRequest;
import com.yashyn.travel_adviser.data.dto.TripDto;
import com.yashyn.travel_adviser.data.entities.Trip;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.data.mapper.TripMapper;
import com.yashyn.travel_adviser.data.repositories.TripRepository;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.TripNotFoundException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripMapper tripMapper;
    private final ImageService imageService;

    /*private final ChatClient chatClient;
    public TripService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }*/

    @Transactional
    public TripDto createTrip(CreateTripRequest cmd, String login) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));

        Trip trip = tripMapper.toEntity(cmd, user);
        imageService.attachImage(trip);
        tripRepository.save(trip);

        return tripMapper.toDto(trip);
    }

    @Transactional(readOnly = true)
    public List<TripDto> getUserTrips(String login) {
        return tripMapper.toDto(tripRepository.findByUser_Login(login));
    }

    public void markTripAsCompleted(Long tripId, String login) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NoSuchElementException("Trip not found"));

        if (!trip.getUser().getLogin().equals(login)) {
            throw new AccessDeniedException("You are not the owner of this trip.");
        }

        trip.setCompleted(true);
        tripRepository.save(trip);
    }

    public String generateAdvice(Long  tripId, String login) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NoSuchElementException("Trip not found"));

        if (!trip.getUser().getLogin().equals(login)) {
            throw new AccessDeniedException("You are not the owner of this trip.");
        }

        if (trip.getAdvice() !=  null) {
            return trip.getAdvice() + "OLD";
        }

        String generatedAdvice = "You are a professional travel assistant. Based on your preferences: cities " + trip.getCities() +
                "and budget " + trip.getBudget() + ", generate a quick tip on where to go.";

        trip.setAdvice(generatedAdvice);
        tripRepository.save(trip);
        return generatedAdvice;

        /*return chatClient
                .prompt(prompt)
                .call()
                .content();*/
    }

    @Transactional(readOnly = true)
    public TripDto getTripDetails(Long id, String login) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found: %d".formatted(id)));

        assertOwner(trip, login);
        return tripMapper.toDto(trip);
    }

    private void assertOwner(Trip trip, String login) {
        if (!trip.getUser().getLogin().equals(login)) {
            throw new AccessDeniedException("You do not own this trip");
        }
    }
}
