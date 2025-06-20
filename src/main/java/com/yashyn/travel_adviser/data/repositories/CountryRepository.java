package com.yashyn.travel_adviser.data.repositories;

import com.yashyn.travel_adviser.data.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
