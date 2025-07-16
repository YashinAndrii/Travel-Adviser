package com.yashyn.travel_adviser.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "travel_advices")
@NoArgsConstructor
@AllArgsConstructor
public class TravelAdvice {
    @Id
    private UUID adviceId;
    @OneToOne(cascade = CascadeType.ALL)
    private Post post;
    private boolean isTransportIncluded;
    private int amountOfPeople;
    private String plan;
}
