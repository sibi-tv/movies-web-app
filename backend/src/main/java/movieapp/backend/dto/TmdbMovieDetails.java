package movieapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieDetails(
        long id,
        String title,
        String overview,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("release_date") String releaseDate,
        Integer runtime,
        Double popularity,
        @JsonProperty("vote_average") Double voteAverage,
        List<Genre> genres
) {
    public record Genre(
            long id,
            String name
    ) {}
}
