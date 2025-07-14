package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.mapper.CreatePostMapper;
import com.yashyn.travel_adviser.data.mapper.PostMapper;
import com.yashyn.travel_adviser.data.repositories.PostRepository;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.PostNotFoundException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final static int AMOUNT_OF_ADVICE_VARIANTS = 3;
    private final static int PREMIUM_AMOUNT_OF_ADVICE_VARIANTS = 5;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final CreatePostMapper createPostMapper;
    private final ChatClient chatClient;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostDto> getPostsByUsername(String username) {
        return postRepository.findByUserUsername(username)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostDto> getPostsByUserId(UUID userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto getPostById(UUID id) {
        return postRepository.findById(id)
                .map(postMapper::toDto)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Transactional
    public PostDto createPost(CreatePostDto dto) { //TODO take userId from auth
        var post = createPostMapper.toEntity(dto);
        return postMapper.toDto(postRepository.save(post));
    }

    @Transactional
    public PostDto updatePost(UUID id, PostDto dto) {
        var existing = postRepository.findById(id).orElseThrow();
        existing.setPhotos(dto.getPhotos());
        existing.setCountries(dto.getCountries());
        existing.setCities(dto.getCities());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setType(TripType.valueOf(dto.getType()));
        existing.setBudget(dto.getBudget());
        existing.setPlanned(dto.isPlanned());
        return postMapper.toDto(postRepository.save(existing));
    }

    public String generateTravelAdvice(CreatePostDto postDto) {
        String prompt = String.format("""
                You are a seasoned travel consultant and trip budgeting specialist.
                
                **Input data**
                {
                  "countries": {countries},                    // Set<String>
                  "cities": {cities},                          // Set<String>
                  "startDate": "{startDate}",                  // ISO-8601  (e.g., 2025-08-01)
                  "endDate": "{endDate}",                      // ISO-8601
                  "budget": {budget},                          // Decimal, total trip budget (currency as specified by user)
                  "amountOfPeople": {amountOfPeople},          // int
                  "isTransportIncludedInBudget": {isTransportIncludedInBudget}, // boolean
                  "type": "{type}",                            // e.g. Adventure | Romantic | Family | Gastronomic …
                  "plannedNote": "{plannedNote}",              // free-form wishes (“love mountains”, “want to focus on museums”, etc.)
                  "placesWhereAlreadyWas": {placesWhereAlreadyWas} // Set<String> — places to exclude
                }
                
                **Task**
                1. Create a detailed **day-by-day** itinerary between startDate and endDate (inclusive).  
                2. For each day, specify:  
                   • city/location;  
                   • short description of the day (“in the morning… / in the afternoon… / in the evening…”);  
                   • recommended activities (3–6 items, based on type and plannedNote);  
                   • links or names of key landmarks;  
                   • meal options (breakfast, lunch, dinner) with average per-person cost;  
                   • transportation for the day with route and approximate cost;  
                   • accommodation (type, name, average cost per night *for all*);  
                   • separate “estimatedCost” lines for: transport, accommodation, food, activities, misc.  
                3. Calculate “dayTotalCost” at the end of each day.  
                4. After the plan, include a **“budgetSummary”** section:  
                   • totalTransport, totalAccommodation, totalFood, totalActivities, totalMisc, grandTotal.  
                   • compare grandTotal with budget and add “withinBudget”: true/false.  
                   • if isTransportIncludedInBudget == false, calculate transport separately and include “budgetExcludingTransport”.  
                5. Apply amountOfPeople in calculations (multiply prices accordingly).  
                6. Do not include any cities from placesWhereAlreadyWas. If overlaps occur, suggest alternatives within the same countries.  
                7. Use realistic average 2025 prices for the selected countries and cities (hostel / 3★ hotel, public transport, taxi, car-rental, etc.).  
                8. Output must be a **valid JSON** with this structure:
                
                ```json
                {
                  "tripSummary": "brief summary in one or two sentences",
                  "dailyPlan": [
                    {
                      "date": "YYYY-MM-DD",
                      "city": "…",
                      "description": "…",
                      "activities": [
                        {
                          "title": "…",
                          "details": "…",
                          "estimatedCost": 45.0
                        }
                      ],
                      "meals": {
                        "breakfast": { "place": "…", "estimatedCost": 8.5 },
                        "lunch":     { "place": "…", "estimatedCost": 15.0 },
                        "dinner":    { "place": "…", "estimatedCost": 22.0 }
                      },
                      "transport": {
                        "mode": "train | bus | flight | metro | car-rental | taxi",
                        "route": "…",
                        "estimatedCost": 30.0
                      },
                      "accommodation": {
                        "type": "hostel | hotel 3★ | airbnb …",
                        "name": "…",
                        "estimatedCost": 120.0
                      },
                      "misc": [
                        { "item": "city tax", "estimatedCost": 3.0 }
                      ],
                      "costBreakdown": {
                        "transport": 30.0,
                        "accommodation": 120.0,
                        "food": 45.5,
                        "activities": 60.0,
                        "misc": 3.0,
                        "dayTotalCost": 258.5
                      }
                    }
                    // ... all days ...
                  ],
                  "budgetSummary": {
                    "totalTransport": 210.0,
                    "totalAccommodation": 840.0,
                    "totalFood": 410.0,
                    "totalActivities": 360.0,
                    "totalMisc": 24.0,
                    "grandTotal": 1844.0,
                    "budgetExcludingTransport": 1634.0, // only if transport is not included
                    "withinBudget": true
                  }
                }
                **Style requirements**
                – Write in English, but keep names of attractions and restaurants in original language.  
                – Round all amounts to one decimal place.  
                – Do not include comments or extra explanation outside the specified JSON.
                """);


        postDto.setTravelAdvice(prompt);
        postRepository.save(createPostMapper.toEntity(postDto));
        //return prompt;

        return chatClient
                .prompt(prompt)
                .options(ChatOptions.builder().temperature(0.6).build())
                .call()
                .content();
    }

    public List<String> generateSetOfTravelAdvices(CreatePostDto postDto) {
        List<String> setOfAdvices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            setOfAdvices.add(generateTravelAdvice(postDto));
        }

        return setOfAdvices;
    }

    @Transactional
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}