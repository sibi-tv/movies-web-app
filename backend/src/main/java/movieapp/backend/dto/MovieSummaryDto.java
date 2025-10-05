package movieapp.backend.dto;

public record MovieSummaryDto(
        long id,
        String title,
        String imageUrl,
        double voteAverage,
        String releaseDate
) {}
