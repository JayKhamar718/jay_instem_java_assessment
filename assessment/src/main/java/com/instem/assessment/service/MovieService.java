package com.instem.assessment.service;

import com.instem.assessment.dto.Movie;
import com.instem.assessment.exception.MovieNotFoundException;
import com.instem.assessment.repository.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Page<Movie> getLatestMovies(final Pageable pageable) {
        final List<Movie> sortedMovies = movieRepository.getAllMovies().stream()
                .sorted(Comparator.comparingInt(Movie::getYear).reversed())
                .collect(Collectors.toList());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), sortedMovies.size());

        final List<Movie> pageContent = sortedMovies.subList(start, end);
        return new PageImpl<>(pageContent, pageable, sortedMovies.size());
    }

    public List<Movie> searchMovies(final String keyword) {
        final List<Movie> results = movieRepository.searchMovies(keyword);
        if (results.isEmpty()) {
            throw new MovieNotFoundException("No movies found with keyword: " + keyword);
        }
        return results;
    }

    public Movie getMovieByTitle(final String title) {
        final Movie movie = movieRepository.getMovieByTitle(title);
        if (Objects.isNull(movie)) {
            throw new MovieNotFoundException("Movie not found: " + title);
        }
        return movie;
    }
}
