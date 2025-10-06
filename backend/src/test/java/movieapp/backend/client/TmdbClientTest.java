package movieapp.backend.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import movieapp.backend.config.TmdbApiProperties;
import movieapp.backend.dto.TmdbImageBaseUrl;
import movieapp.backend.dto.TmdbMovieDetails;
import movieapp.backend.dto.TmdbTrendingMovies;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TmdbClientTest {

    private static WireMockServer wireMockServer;
    private TmdbClient tmdbClient;

    @BeforeAll
    static void start() {
        wireMockServer = new WireMockServer(0);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void stop() {
        wireMockServer.stop();
    }

    @AfterEach
    void reset() {
        wireMockServer.resetAll();
    }

    @BeforeEach
    void setup() {
        RestClient restClient = RestClient.builder()
                .defaultHeader("Authorization", "Bearer dummy")
                .build();

        TmdbApiProperties apiProps = new TmdbApiProperties("http://localhost:" + wireMockServer.port() + "/3");
        tmdbClient = new TmdbClientImplementation(restClient, apiProps);
    }

    /**
     * Trending Movies Happy Path Test Case
     */
    @Test
    void trendingMovies_Success() {

        stubFor(get(urlEqualTo("/3/trending/movie/day"))
                .willReturn(okJson("""
                        {"results":[
                            {"id":550,"title":"Fight Club","poster_path":"/5TiwfWEaPSwD20uwXjCTUqpQX70.jpg","vote_average":8.4},
                            {"id": 1236470, "title": "The Lost Bus", "poster_path": "/zpygCOYY1DPBkeUsrrznLRN5js5.jpg", "vote_average": 7}
                        ]}
                        """)));

        int resultsSize = 2;
        long movieId1 = 550;
        long movieId2 = 1236470;

        TmdbTrendingMovies trending = tmdbClient.fetchTrendingMovies("day");

        Assertions.assertEquals(resultsSize, trending.results().size());
        Assertions.assertEquals(movieId1, trending.results().get(0).id());
        Assertions.assertEquals(movieId2, trending.results().get(1).id());
    }

    /**
     * Trending Movies Exception Handling Test Case
     */
    @Test
    void trendingMovies_BadGatewayExceptionHandling() {

        stubFor(get(urlEqualTo("/3/trending/movie/week"))
                .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        ResponseStatusException thrown = Assertions.assertThrows(
                ResponseStatusException.class, () -> tmdbClient.fetchTrendingMovies("week"));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, thrown.getStatusCode());
    }

    /**
     * Movie Details Happy Path Test Case
     */
    @Test
    void movieDetails_Success() {

        long movieId = 5175;
        String title = "Rush Hour 2";

        stubFor(get(urlEqualTo("/3/movie/" + movieId))
                .willReturn(okJson("""
                    {
                      "id":5175,
                      "title":"Rush Hour 2",
                      "overview":"Carter and Lee head to Hong Kong...",
                      "poster_path":"/rush-hour-2.jpg",
                      "runtime":90,
                      "release_date":"2001-08-03",
                      "genres":[{"id":28,"name":"Action"},{"id":35,"name":"Comedy"}]
                    }
                    """)));

        TmdbMovieDetails details = tmdbClient.fetchMovieById(movieId);

        Assertions.assertEquals(movieId, details.id());
        Assertions.assertEquals(title, details.title());
    }

    /**
     * Movie Details Exception Handling Test Cases
     */
    @Test
    void movieDetails_NotFoundExceptionHandling() {

        long invalidMovieId = 999999999L;

        stubFor(get(urlEqualTo("/3/movie/" + invalidMovieId))
                .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        ResponseStatusException thrown = Assertions.assertThrows(
                ResponseStatusException.class, () -> tmdbClient.fetchMovieById(invalidMovieId)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());

        String notFoundErrorMessage = "TMDB movie not found";
        Assertions.assertTrue(thrown.getMessage().contains(notFoundErrorMessage));
    }

    @Test
    void movieDetails_BadGatewayExceptionHandling() {

        long movieId = 5175;

        stubFor(get(urlEqualTo("/3/movie/" + movieId))
                .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        ResponseStatusException thrown = Assertions.assertThrows(
                ResponseStatusException.class, () -> tmdbClient.fetchMovieById(movieId));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, thrown.getStatusCode());
    }

    /**
     * Image Base Url Configuration Happy Path Test Case
     */
    @Test
    void imageBaseUrlConfig_Success() {

        stubFor(get(urlEqualTo("/3/configuration"))
                .willReturn(okJson("""
                    {
                      "images": {
                        "base_url": "http://image.tmdb.org/t/p/",
                        "secure_base_url": "https://image.tmdb.org/t/p/",
                        "backdrop_sizes": ["w300","w780","w1280","original"],
                        "logo_sizes": ["w45","w92","w154","w185","w300","w500","original"],
                        "poster_sizes": ["w92","w154","w185","w342","w500","w780","original"],
                        "profile_sizes": ["w45","w185","h632","original"],
                        "still_sizes": ["w92","w185","w300","original"]
                      },
                      "change_keys": []
                    }
                """)));

        String baseUrl = "https://image.tmdb.org/t/p/";
        TmdbImageBaseUrl imageConfig = tmdbClient.fetchImageUrlAndSize();

        Assertions.assertEquals(baseUrl, imageConfig.images().secureBaseUrl());
        Assertions.assertTrue(Arrays.asList(imageConfig.images().posterSizes()).contains("w500"));
    }

    /**
     * Image Base Url Configuration Exception Handling Test Case
     */
    @Test
    void imageBaseUrlConfig_BadGatewayExceptionHandling() {

        stubFor(get(urlEqualTo("/3/configuration"))
                .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        ResponseStatusException thrown = Assertions.assertThrows(
                        ResponseStatusException.class, () -> tmdbClient.fetchImageUrlAndSize());

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, thrown.getStatusCode());
    }
}
