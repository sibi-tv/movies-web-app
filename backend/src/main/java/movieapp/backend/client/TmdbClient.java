package movieapp.backend.client;

import movieapp.backend.dto.TmdbImageBaseUrl;
import movieapp.backend.dto.TmdbMovieDetails;
import movieapp.backend.dto.TmdbPosterPaths;
import movieapp.backend.dto.TmdbTrendingMovies;

public interface TmdbClient {
    // calls https://api.themoviedb.org/3/trending/movie/{time_window}
    TmdbTrendingMovies fetchTrendingMovies(String window);

    // calls https://api.themoviedb.org/3/movie/{movie_id}
    TmdbMovieDetails fetchMovieById(long id);

    // calls https://api.themoviedb.org/3/configuration
    TmdbImageBaseUrl fetchImageUrlAndSize();

    // calls https://api.themoviedb.org/3/movie/{movie_id}/images?language=""&include_image_language. Used to fetch the poster_paths
    TmdbPosterPaths fetchPosterPaths(long id, String language, String includeImageLanguage); //
}
