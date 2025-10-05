package movieapp.backend.dto;

import java.util.List;

public record MovieDetailsDto(
        long id,
        String title,
        String overview,
        List<String> genres,
        int runtime,
        String releaseDate,
        String imageUrl,
        double voteAverage
) {}

