package movieapp.backend.service;

import movieapp.backend.client.TmdbClient;
import movieapp.backend.config.CacheConfiguration;
import movieapp.backend.dto.TmdbImageBaseUrl;
import movieapp.backend.dto.TmdbTrendingMovies;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@Import({ CacheConfiguration.class, MovieServiceImplementation.class })
public class MovieServiceCacheTest {

    @MockitoBean
    TmdbClient tmdbClient;

    @Autowired
    MovieService movieService;

    @Test
    void getTrendingMovies_verifyingSingleTmdbApiCallOnly() {

        TmdbTrendingMovies trendingMoviesByDay = new TmdbTrendingMovies(List.of(
                new TmdbTrendingMovies.Movie(1236470, "The Lost Bus",
                        "/lL4zajQbvvo63z2CNu36Ue6wpBX.jpg", 6.872, "2025-09-19",
                        "A determined father risks everything to rescue . . ."),
                new TmdbTrendingMovies.Movie(941109, "Play Dirty",
                        "/k6tdiMTO39RQj3dhfspuzprfoe0.jpg", 6.5, "2025-09-30",
                        "Expert thief Parker gets a shot at a major heist . . ."),
                new TmdbTrendingMovies.Movie(1242404, "Steve",
                        "/sb8Aq1RohASXyjT0uEOUUZJi5oI.jpg", 6.803, "2025-09-19",
                        "Over one intense day, the devoted head teacher . . .")
        ));

        TmdbTrendingMovies trendingMoviesByWeek = new TmdbTrendingMovies(List.of(
                new TmdbTrendingMovies.Movie(941109, "Play Dirty",
                        "/k6tdiMTO39RQj3dhfspuzprfoe0.jpg", 6.5, "2025-09-30",
                        "Expert thief Parker gets a shot at a major heist . . ."),
                new TmdbTrendingMovies.Movie(1236470, "The Lost Bus",
                        "/lL4zajQbvvo63z2CNu36Ue6wpBX.jpg", 6.872, "2025-09-19",
                        "A determined father risks everything to rescue . . ."),
                new TmdbTrendingMovies.Movie(1245993, "Caught Stealing",
                        "/41043LV7mfluH8iXEUPVseCDAv5.jpg", 7.018, "2025-08-26",
                        "Burned-out ex-baseball player Hank Thompson . . .")
        ));


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


        when(tmdbClient.fetchTrendingMovies("day")).thenReturn(trendingMoviesByDay);
        when(tmdbClient.fetchTrendingMovies("week")).thenReturn(trendingMoviesByWeek);
        when(tmdbClient.fetchImageUrlAndSize()).thenReturn(configuration);

        movieService.getTrendingMovies(TrendingWindow.DAY);
        movieService.getTrendingMovies(TrendingWindow.WEEK);

        movieService.getTrendingMovies(TrendingWindow.DAY);
        movieService.getTrendingMovies(TrendingWindow.WEEK);

        // Each window should be fetched once
        verify(tmdbClient, times(1)).fetchTrendingMovies("day");
        verify(tmdbClient, times(1)).fetchTrendingMovies("week");

        // Called once per window
        verify(tmdbClient, times(2)).fetchImageUrlAndSize();
    }

}
