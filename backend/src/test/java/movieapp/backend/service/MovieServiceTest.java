package movieapp.backend.service;

import movieapp.backend.client.TmdbClient;
import movieapp.backend.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Both test cases are happy path test cases.
 * MovieService is set up so that it only passes appropriate inputs to TmdbClient.
 * MovieController handles the exceptions and bad request possibilities
 */

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    TmdbClient tmdbClient;

    @InjectMocks
    MovieServiceImplementation movieServiceImplementation;

    @Test
    void getTrending_mapsTmdbToSummaries() {

        long movieId = 5175;

        TmdbTrendingMovies movieList = new TmdbTrendingMovies(List.of(
                new TmdbTrendingMovies.Movie(
                        movieId,
                        "Rush Hour 2",
                        "/nmllsevWzx7XtrlARs3hHJn5Pf.jpg",
                        6.749,
                        "2001-08-03",
                        "It's vacation time for Carter  as he finds himself alongside Lee in Hong Kong wishing . . ."
                )));
        when(tmdbClient.fetchTrendingMovies("day")).thenReturn(movieList);


        String[] posterSizes = new String[] {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
        String[] backdropSizes = new String[] {"w300", "w780", "w1280", "original"};
        String[] profileSizes = new String[] {"w45", "w185", "h632", "original"};

        TmdbImageBaseUrl.Images images = new TmdbImageBaseUrl.Images(
                "http://image.tmdb.org/t/p/",
                "http://image.tmdb.org/t/p/",
                posterSizes,
                backdropSizes,
                profileSizes
        );
        TmdbImageBaseUrl configuration = new TmdbImageBaseUrl(images);
        when(tmdbClient.fetchImageUrlAndSize()).thenReturn(configuration);

        List<MovieSummaryDto> trendingMovieList = movieServiceImplementation.getTrendingMovies(TrendingWindow.DAY);

        Assertions.assertEquals(1, trendingMovieList.size());
        Assertions.assertEquals(movieId, trendingMovieList.get(0).id());
        Assertions.assertEquals("http://image.tmdb.org/t/p/w185/nmllsevWzx7XtrlARs3hHJn5Pf.jpg", trendingMovieList.get(0).imageUrl());
        verify(tmdbClient, times(1)).fetchTrendingMovies("day");
    }

    @Test
    void getMovie_mapsDetailsAndPosterUrl() {

        long movieId = 5175;

        List<TmdbMovieDetails.Genre> genres = List.of(
                new TmdbMovieDetails.Genre(28, "Action"),
                new TmdbMovieDetails.Genre(35, "Comedy"),
                new TmdbMovieDetails.Genre(80, "Crime")
        );

        TmdbMovieDetails movieDetails = new TmdbMovieDetails(
                movieId,
                "Rush Hour 2",
                "It's vacation time for Carter  as he finds himself alongside Lee in Hong Kong wishing . . .",
                "/nmllsevWzx7XtrlARs3hHJn5Pf.jpg",
                "2001-08-03",
                90,
                7.6897,
                6.749,
                genres
        );
        String[] posterSizes = new String[] {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
        String[] backdropSizes = new String[] {"w300", "w780", "w1280", "original"};
        String[] profileSizes = new String[] {"w45", "w185", "h632", "original"};
        TmdbImageBaseUrl.Images imageDetails = new TmdbImageBaseUrl.Images(
                "http://image.tmdb.org/t/p/",
                "https://image.tmdb.org/t/p/",
                posterSizes,
                backdropSizes,
                profileSizes
        );
        TmdbImageBaseUrl images = new TmdbImageBaseUrl(imageDetails);
        when(tmdbClient.fetchMovieById(movieId)).thenReturn(movieDetails);
        when(tmdbClient.fetchImageUrlAndSize()).thenReturn(images);

        MovieDetailsDto movie = movieServiceImplementation.getMovie(movieId);
        Assertions.assertEquals(movieId, movie.id());
        Assertions.assertTrue(movie.imageUrl().contains("w500"));
        verify(tmdbClient).fetchMovieById(movieId);
        verify(tmdbClient).fetchImageUrlAndSize();
    }
}
