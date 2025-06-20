package com.yashyn.travel_adviser.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<String> countries;

    @ElementCollection
    private List<String> cities;

    @Enumerated(EnumType.STRING)
    private TripType type;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal budget;

    private boolean includeTransport;

    private boolean hasChildren;
    @Column(length = 1000)
    private String additionalInfo;
    private boolean completed;

    private String advice;

    private String imagePath;

    @ManyToOne
    @JsonIgnore
    private User user;
}

