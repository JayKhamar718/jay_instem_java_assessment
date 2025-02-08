package com.instem.assessment.dto;

import lombok.Data;

@Data
public class Movie {
    private int year;
    private String title;
    private MovieInfo info;
}
