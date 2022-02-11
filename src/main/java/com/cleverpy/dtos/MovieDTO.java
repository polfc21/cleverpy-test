package com.cleverpy.dtos;

import com.cleverpy.entities.MovieEntity;
import com.cleverpy.validators.FilmGenre;
import com.cleverpy.validators.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
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

    @NonNull
    @Year
    private Integer year;

    @NotBlank
    private String language;

    @Positive
    @NonNull
    private Integer duration;

    @NonNull
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
        if (movie.getDirector() != null) {
            this.directorDTO = new DirectorDTO(movie.getDirector());
        }
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
