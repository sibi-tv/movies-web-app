package movieapp.backend.client;

import lombok.AllArgsConstructor;
import movieapp.backend.config.TmdbApiProperties;
import movieapp.backend.dto.TmdbImageBaseUrl;
import movieapp.backend.dto.TmdbMovieDetails;
import movieapp.backend.dto.TmdbPosterPaths;
import movieapp.backend.dto.TmdbTrendingMovies;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class TmdbClientImplementation implements TmdbClient{

    private final RestClient restClient;
    private final TmdbApiProperties tmdbApiProperties;

    @Override
    public TmdbTrendingMovies fetchTrendingMovies(String window) {
        String endpoint = "/trending/movie/" + window;
        String fullUri = tmdbApiProperties.getBaseUrl() + endpoint;

        try {
            return restClient.get()
                    .uri(fullUri)
                    .retrieve()
                    .body(TmdbTrendingMovies.class);
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "TMDB error: " + e.getStatusCode().value(), e);
        }
    }

    @Override
    public TmdbMovieDetails fetchMovieById(long id) {
        String endpoint = "/movie/" + id;
        String fullUri = tmdbApiProperties.getBaseUrl() + endpoint;

        try {
            return restClient.get()
                    .uri(fullUri)
                    .retrieve()
                    .body(TmdbMovieDetails.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TMDB movie not found", e);
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "TMDB error: " + e.getStatusCode().value(), e);
        }
    }

    @Override
    public TmdbImageBaseUrl fetchImageUrlAndSize() {
        String endpoint = "/configuration";
        String fullUri = tmdbApiProperties.getBaseUrl() + endpoint;

        try {
            return restClient.get()
                    .uri(fullUri)
                    .retrieve()
                    .body(TmdbImageBaseUrl.class);
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "TMDB error: " + e.getStatusCode().value(), e);
        }
    }

    @Override
    public TmdbPosterPaths fetchPosterPaths(long id, String language, String includeImageLanguage) {
        String endpoint = "/movie/" + id + "/images?language=" + language + "&include_image_language=" + includeImageLanguage;
        String fullUri = tmdbApiProperties.getBaseUrl() + endpoint;

        try {
            return restClient.get()
                    .uri(fullUri)
                    .retrieve()
                    .body(TmdbPosterPaths.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TMDB movie not found", e);
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "TMDB error: " + e.getStatusCode().value(), e);
        }
    }

}
