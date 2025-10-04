package movieapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbTrendingMovies(List<Movie> results) {
    public record Movie(
            long id,
            String title,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("vote_average") Double voteAverage,
            @JsonProperty("release_date") String releaseDate,
            String overview
    ) {}
}
