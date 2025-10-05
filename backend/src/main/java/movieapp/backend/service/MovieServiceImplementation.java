package movieapp.backend.service;

import lombok.AllArgsConstructor;
import movieapp.backend.client.TmdbClient;
import movieapp.backend.dto.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MovieServiceImplementation implements MovieService{

    private final TmdbClient tmdbClient;

    @Override
    @Cacheable(cacheNames = "trendingMoviesCache", key = "#window.name()", unless = "#result == null || #result.isEmpty()")
    public List<MovieSummaryDto> getTrendingMovies(TrendingWindow window) {
        String windowParam = window.returnWindowType();
        TmdbTrendingMovies movieList = tmdbClient.fetchTrendingMovies(window.returnWindowType());
        TmdbImageBaseUrl imageBaseUrl = tmdbClient.fetchImageUrlAndSize();
        String smallPosterSize = imageBaseUrl.images().posterSizes()[2]; // Size is "w185"

        return movieList.results().stream().map(movie -> new MovieSummaryDto(
                movie.id(),
                movie.title(),
                imageBaseUrl.images().baseUrl() + smallPosterSize + movie.posterPath(),
                movie.voteAverage() == null ? 0.0 : movie.voteAverage(),
                movie.releaseDate()
        )).toList();
    }

    @Override
    public MovieDetailsDto getMovie(long id) {
        TmdbMovieDetails movieDetails = tmdbClient.fetchMovieById(id);
        TmdbImageBaseUrl imageBaseUrl = tmdbClient.fetchImageUrlAndSize();
        String largePosterSize = imageBaseUrl.images().posterSizes()[4]; // Size is "w500"

        return new MovieDetailsDto(
                movieDetails.id(),
                movieDetails.title(),
                movieDetails.overview(),
                movieDetails.genres().stream().map(TmdbMovieDetails.Genre::name).toList(),
                movieDetails.runtime(),
                movieDetails.releaseDate(),
                imageBaseUrl.images().baseUrl() + largePosterSize + movieDetails.posterPath(),
                movieDetails.voteAverage() == null ? 0.0 : movieDetails.voteAverage()
                //movieDetails.voteAverage()
        );
    }
}
