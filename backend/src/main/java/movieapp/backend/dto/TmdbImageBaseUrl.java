package movieapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbImageBaseUrl(
        Images images
) {
    public record Images(
            @JsonProperty("base_url") String baseUrl,
            @JsonProperty("secure_base_url") String secureBaseUrl,
            @JsonProperty("poster_sizes") String[] posterSizes,
            @JsonProperty("backdrop_sizes") String[] backdropSizes,
            @JsonProperty("profile_sizes") String[] profileSizes
    ) {}
}
