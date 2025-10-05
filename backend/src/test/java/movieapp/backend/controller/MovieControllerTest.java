package movieapp.backend.controller;

import movieapp.backend.dto.MovieDetailsDto;
import movieapp.backend.dto.MovieSummaryDto;
import movieapp.backend.dto.TmdbImageBaseUrl;
import movieapp.backend.dto.TmdbMovieDetails;
import movieapp.backend.service.MovieService;
import movieapp.backend.service.TrendingWindow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MovieService movieService;

    @Test
    void trendingMovies_happyPathScenario() throws Exception {

        long movieId = 5175;

        List<MovieSummaryDto> movieSummaryDto = List.of( new MovieSummaryDto(
                movieId,
                "Rush Hour 2",
                "/nmllsevWzx7XtrlARs3hHJn5Pf.jpg",
                6.749,
                "2001-08-03"
        ));

        when(movieService.getTrendingMovies(TrendingWindow.DAY)).thenReturn(movieSummaryDto);

        mockMvc.perform(get("/api/trending/movie").param("window", "day"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(movieId));
    }

    @Test
    void trendingMovies_handlingInvalidWindow() throws Exception {
        mockMvc.perform(get("/api/trending/movie").param("window", "month"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void movieDetails_happyPathScenario() throws Exception {

        long movieId = 5175;

        List<String> genres = List.of("Action", "Comedy", "Crime");

        MovieDetailsDto movieDetails = new MovieDetailsDto(
                movieId,
                "Rush Hour 2",
                "It's vacation time for Carter  as he finds himself alongside Lee in Hong Kong wishing . . .",
                genres,
                90,
                "2001-08-03",
                "http://image.tmdb.org/t/p/w500/nmllsevWzx7XtrlARs3hHJn5Pf.jpg",
                6.749
        );

        when(movieService.getMovie(movieId)).thenReturn(movieDetails);

        mockMvc.perform(get("/api/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.title").value("Rush Hour 2"))
                .andExpect(jsonPath("$.releaseDate").value("2001-08-03"))
                .andExpect(jsonPath("$.voteAverage").value(6.749));

    }

    @Test
    void movieDetails_handlingInvalidId() throws Exception {

        long invalidMovieId = 0;

        mockMvc.perform(get("/api/movies/{id}", invalidMovieId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void movieDetails_handlingNonExistentId() throws Exception {

        long nonExistentId = 999;

        when(movieService.getMovie(nonExistentId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/api/movies/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void movieDetails_handlingBadGateway() throws Exception {

        long movieId = 5175;

        when(movieService.getMovie(movieId)).thenThrow(new ResponseStatusException(HttpStatus.BAD_GATEWAY));
        mockMvc.perform(get("/api/movies/{id}", movieId))
                .andExpect(status().isBadGateway());
    }

}
