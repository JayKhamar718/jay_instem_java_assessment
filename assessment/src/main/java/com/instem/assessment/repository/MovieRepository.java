package com.instem.assessment.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instem.assessment.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {
    private final List<Movie> movies;

    @Autowired
    public MovieRepository(final ObjectMapper objectMapper) throws IOException {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("moviedata.json");
        if (Objects.isNull(inputStream)) {
            throw new RuntimeException("moviedata.json not found");
        }
        movies = objectMapper.readValue(inputStream, new TypeReference<List<Movie>>() {
        });
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public List<Movie> searchMovies(String keyword) {
        return movies.stream()
                .filter(movie -> matchesSearch(movie, keyword.toLowerCase()))
                .sorted(Comparator.comparingInt(Movie::getYear).reversed())
                .collect(Collectors.toList());
    }

    public Movie getMovieByTitle(final String title) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    private boolean matchesSearch(final Movie movie, final String keyword) {
        return isMatch(movie.getTitle(), keyword) ||
                isMatch(movie.getInfo().getPlot(), keyword) ||
                isMatch(movie.getInfo().getDirectors(), keyword) ||
                isMatch(movie.getInfo().getGenres(), keyword) ||
                isMatch(movie.getInfo().getActors(), keyword);
    }

    private boolean isMatch(final String field, final String keyword) {
        return Objects.nonNull(field) && field.toLowerCase().contains(keyword);
    }

    private boolean isMatch(final List<String> list, final String keyword) {
        return Objects.nonNull(list) && list.stream().anyMatch(item -> isMatch(item, keyword));
    }

}
