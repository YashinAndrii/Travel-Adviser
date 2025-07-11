package com.yashyn.travel_adviser.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@NamedEntityGraph(
        name = "Post.countriesCitiesPhotos",
        attributeNodes = {
                @NamedAttributeNode("countries"),
                @NamedAttributeNode("cities"),
                @NamedAttributeNode("photos")
        }
)
public class Post {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "posts_photos", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "photo_url")
    private Set<String> photos;

    @ElementCollection
    @CollectionTable(name = "posts_countries", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "country")
    private Set<String> countries;

    @ElementCollection
    @CollectionTable(name = "posts_cities", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "city")
    private Set<String> cities;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TripType type;

    private BigDecimal budget;

    private boolean isPlanned;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    private String plannedNote;
    private String description;
}
