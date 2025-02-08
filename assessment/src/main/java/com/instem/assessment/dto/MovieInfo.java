package com.instem.assessment.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class MovieInfo {
    private List<String> directors;
    @JsonAlias({"release_date", "releaseDate"})
    private Instant releaseDate;
    private double rating;
    private List<String> genres;
    @JsonAlias({"image_url", "imageUrl"})
    private String imageUrl;
    private String plot;
    private int rank;
    @JsonAlias({"running_time_secs", "runningTimeSecs"})
    private int runningTimeSecs;
    private List<String> actors;
}