package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.MovieEntity;
import com.cleverpy.domain.validators.FilmGenre;
import com.cleverpy.domain.validators.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Integer id;

    @NotBlank
    private String title;

    @NotNull
    @Year
    private Integer year;

    @NotBlank
    private String language;

    @Positive
    @NotNull
    private Integer duration;

    @NotNull
    @FilmGenre
    private String filmGenre;

    private DirectorDTO directorDTO;

    private List<ActorDTO> castDTO;

    public MovieDTO(MovieEntity movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.language = movie.getLanguage();
        this.duration = movie.getDuration();
        this.filmGenre = movie.getFilmGenreType().name();
        this.directorDTO = new DirectorDTO(movie.getDirector());
        if (movie.getCast() != null) {
            this.castDTO = movie.getCast()
                    .stream()
                    .map(ActorDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public MovieEntity toMovieEntity() {
        return new MovieEntity(this.title, this.year, this.language, this.duration, this.filmGenre.toUpperCase());
    }

}
