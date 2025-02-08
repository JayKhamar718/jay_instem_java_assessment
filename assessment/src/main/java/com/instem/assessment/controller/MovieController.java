package com.instem.assessment.controller;

import com.instem.assessment.dto.Movie;
import com.instem.assessment.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<Movie>> getLatestMovies(final Pageable pageable) {
        return ResponseEntity.ok(movieService.getLatestMovies(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam final String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable final String title) {
        return ResponseEntity.ok(movieService.getMovieByTitle(title));
    }
}