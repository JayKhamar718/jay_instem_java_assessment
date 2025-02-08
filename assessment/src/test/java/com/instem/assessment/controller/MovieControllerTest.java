package com.instem.assessment.controller;

import com.instem.assessment.dto.Movie;
import com.instem.assessment.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    private static final String MOVIE_NAME = "Rush";
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    @Test
    void testGetLatestMovies_ShouldReturnPaginatedMovies() {
        final Page<Movie> mockPage = new PageImpl<>(List.of(new Movie(), new Movie(), new Movie(), new Movie()));
        when(movieService.getLatestMovies(any())).thenReturn(mockPage);

        final ResponseEntity<Page<Movie>> response = movieController.getLatestMovies(PageRequest.of(0, 4));

        assertNotNull(response);
        assertEquals(4, response.getBody().getSize());
    }

    @Test
    void testSearchMovies_ShouldReturnSearchResults() {
        final List<Movie> mockMovies = List.of(new Movie());
        when(movieService.searchMovies(MOVIE_NAME)).thenReturn(mockMovies);

        final ResponseEntity<List<Movie>> response = movieController.searchMovies(MOVIE_NAME);

        assertNotNull(response);
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMovieByTitle_ShouldReturnCorrectMovie() {
        final Movie mockMovie = new Movie();
        mockMovie.setTitle(MOVIE_NAME);
        when(movieService.getMovieByTitle(MOVIE_NAME)).thenReturn(mockMovie);

        final ResponseEntity<Movie> response = movieController.getMovieByTitle(MOVIE_NAME);

        assertNotNull(response);
        assertEquals(MOVIE_NAME, response.getBody().getTitle());
    }
}
