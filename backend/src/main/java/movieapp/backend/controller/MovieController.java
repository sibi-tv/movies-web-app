package movieapp.backend.controller;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import movieapp.backend.dto.MovieDetailsDto;
import movieapp.backend.dto.MovieSummaryDto;
import movieapp.backend.service.MovieService;
import movieapp.backend.service.TrendingWindow;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/trending/movie")
    @Cacheable(cacheNames = "trendingMoviesCache", key = "#window.name()", unless = "#result == null || #result.isEmpty()")
    public List<MovieSummaryDto> trendingMovies(@RequestParam(defaultValue = "day") String window) {
        try {
            TrendingWindow trendWindow = TrendingWindow.determineParamWindow(window);
            return movieService.getTrendingMovies(trendWindow);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iae.getMessage(), iae);
        }
    }

    @GetMapping("/movies/{id}")
    public MovieDetailsDto movieDetails(@PathVariable long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id: id must be greater than 0");
        }
        return movieService.getMovie(id);
    }
}
