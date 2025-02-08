package com.instem.assessment.service;

import com.instem.assessment.dto.Movie;
import com.instem.assessment.exception.MovieNotFoundException;
import com.instem.assessment.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private static final String MOVIE_NAME = "Rush";
    private static final String UNKNOWN_MOVIE = "Unknown Movie";
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;

    @Test
    void testGetLatestMovies_ShouldReturnPaginatedMovies() {
        final List<Movie> mockMovies = List.of(new Movie(), new Movie(), new Movie());
        when(movieRepository.getAllMovies()).thenReturn(mockMovies);

        final Page<Movie> page = movieService.getLatestMovies(PageRequest.of(0, 2));

        assertNotNull(page);
        assertEquals(2, page.getSize());
    }

    @Test
    void testSearchMovies_ShouldReturnMatchingMovies() {
        final List<Movie> mockMovies = List.of(new Movie());
        when(movieRepository.searchMovies(MOVIE_NAME)).thenReturn(mockMovies);

        final List<Movie> results = movieService.searchMovies(MOVIE_NAME);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void testSearchMovies_ShouldThrowExceptionIfNotFound() {

        final Exception exception = assertThrows(MovieNotFoundException.class, () -> movieService.searchMovies(UNKNOWN_MOVIE));

        assertEquals("No movies found with keyword: " + UNKNOWN_MOVIE, exception.getMessage());
    }

    @Test
    void testGetMovieByTitle_ShouldReturnCorrectMovie() {
        final Movie mockMovie = new Movie();
        mockMovie.setTitle(MOVIE_NAME);
        when(movieRepository.getMovieByTitle(MOVIE_NAME)).thenReturn(mockMovie);

        final Movie movie = movieService.getMovieByTitle(MOVIE_NAME);

        assertNotNull(movie);
        assertEquals(MOVIE_NAME, movie.getTitle());
    }

    @Test
    void testGetMovieByTitle_ShouldThrowMovieNotFoundException() {
        Exception exception = assertThrows(MovieNotFoundException.class, () -> movieService.getMovieByTitle(UNKNOWN_MOVIE));

        assertEquals("Movie not found: " + UNKNOWN_MOVIE, exception.getMessage());
    }
}
