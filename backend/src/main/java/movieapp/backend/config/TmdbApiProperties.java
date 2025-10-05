package movieapp.backend.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("tmdb")
@NoArgsConstructor
@Getter
@Setter
public class TmdbApiProperties {
    @NotBlank String baseUrl;
    @NotBlank String apiKey;

    public TmdbApiProperties(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
