package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.CreateTripCommand;
import com.yashyn.travel_adviser.data.dto.TripDto;
import com.yashyn.travel_adviser.data.entities.Destination;
import com.yashyn.travel_adviser.data.entities.Trip;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.data.mapper.TripMapper;
import com.yashyn.travel_adviser.data.repositories.TripRepository;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.TripNotFoundException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripMapper tripMapper;
    private final ImageService imageService;

    @Transactional
    public TripDto createTrip(CreateTripCommand cmd, String login) {
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

/*    @Transactional(readOnly = true)
    public TripDto getTripDetails(Long id, String login) {
        Trip trip = findOwnedTrip(id, login);
        return tripMapper.toDto(trip);
    }*/
    /*private final ChatClient chatClient;
    public TripService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }*/

    public Trip createTrip(String login,
                           List<String> countries,
                           List<String> cities,
                           TripType type,
                           LocalDate startDate,
                           LocalDate endDate,
                           BigDecimal budget,
                           boolean includeTransport,
                           boolean hasChildren,
                           String additionalInfo) {

        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + login));

        Trip trip = new Trip();
        trip.setUser(user);
        trip.setCountries(countries);
        trip.setCities(cities);
        trip.setType(type);
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setBudget(budget);
        trip.setIncludeTransport(includeTransport);
        trip.setHasChildren(hasChildren);
        trip.setCompleted(false);
        trip.setAdditionalInfo(additionalInfo);

        String city = trip.getCities().getFirst().toLowerCase();
        String imagePath = "/trip-images/" + city + ".jpg";
        File imageFile = new File("src/main/resources/static" + imagePath);
        if (imageFile.exists()) {
            trip.setImagePath(imagePath);
        } else {
            trip.setImagePath("/trip-images/default.jpg");
        }

        return tripRepository.save(trip);
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

        assertOwner(trip, login);          // централизованная проверка владения
        return tripMapper.toDto(trip);     // отдаём только DTO-проекцию
    }

    private void assertOwner(Trip trip, String login) {
        if (!trip.getUser().getLogin().equals(login)) {
            throw new AccessDeniedException("You do not own this trip");
        }
    }
}
