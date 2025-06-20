package com.yashyn.travel_adviser.data.repositories;

import com.yashyn.travel_adviser.data.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Long> {
    List<Trip> findByUser_Login(String login);
}
