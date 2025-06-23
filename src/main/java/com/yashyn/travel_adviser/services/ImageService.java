package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.entities.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ResourceLoader resourceLoader;

    @Value("${app.images.root}")
    private String imagesRoot;

    @Value("${app.images.default}")
    private String defaultImageUrl;

    public String pickImageForTrip(Trip trip) {
        if (trip.getCities() == null || trip.getCities().isEmpty()) {
            return defaultImageUrl;
        }
        return pickImageForCity(trip.getCities().getFirst());
    }

    public void attachImage(Trip trip) {
        trip.setImagePath(pickImageForTrip(trip));
    }

    private String pickImageForCity(String city) {
        String fileName = city.toLowerCase(Locale.ROOT) + ".jpg";
        String relativeUrl = "/trip-images/" + fileName;
        String candidateLocation = imagesRoot + "/" + fileName;

        Resource resource = resourceLoader.getResource(candidateLocation);
        return resource.exists() ? relativeUrl : defaultImageUrl;
    }
}

