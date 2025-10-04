package movieapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbPosterPaths(
        long id,
        List<Image> backdrops,
        List<Image> posters
) {
    public record Image(
            @JsonProperty("file_path") String filePath,
            Integer width,
            Integer height,
            @JsonProperty("aspect_ratio") Double aspectRatio,
            @JsonProperty("iso_639_1") String languageCode
    ) {}
}
