package com.instem.assessment.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instem.assessment.dto.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryTest {
    private MovieRepository movieRepository;
    private static final String MOVIE_NAME = "Rush";
    private static final String UNKNOWN_MOVIE = "Unknown Movie";

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("moviedata.json");
        final List<Movie> mockMovies = objectMapper.readValue(inputStream, new TypeReference<>() {});

        movieRepository = new MovieRepository(objectMapper);
    }

    @Test
    void testGetAllMovies_ShouldReturnAllMovies() {
        final List<Movie> movies = movieRepository.getAllMovies();
        assertThat(movies).isNotEmpty();
    }

    @Test
    void testSearchMovies_ShouldReturnMatchingMovies() {
        final List<Movie> results = movieRepository.searchMovies("Transformers");
        assertFalse(results.isEmpty());
        assertThat(results.get(0).getTitle()).containsIgnoringCase("Transformers");
    }

    @Test
    void testGetMovieByTitle_ShouldReturnCorrectMovie() {
        final Movie movie = movieRepository.getMovieByTitle(MOVIE_NAME);
        assertNotNull(movie);
        assertEquals(MOVIE_NAME, movie.getTitle());
    }

    @Test
    void testGetMovieByTitle_ShouldReturnNullForNonExistingMovie() {
        final Movie movie = movieRepository.getMovieByTitle(UNKNOWN_MOVIE);
        assertNull(movie);
    }
}
