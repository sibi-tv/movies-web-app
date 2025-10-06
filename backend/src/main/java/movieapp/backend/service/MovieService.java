package movieapp.backend.service;

import movieapp.backend.dto.MovieDetailsDto;
import movieapp.backend.dto.MovieSummaryDto;

import java.util.List;


public interface MovieService {
    List<MovieSummaryDto> getTrendingMovies(TrendingWindow window);
    MovieDetailsDto getMovie(long id);
}
