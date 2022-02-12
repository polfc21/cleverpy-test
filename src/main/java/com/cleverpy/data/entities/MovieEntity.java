package com.cleverpy.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String title;

    @NonNull
    private Integer year;

    @NonNull
    private String language;

    @NonNull
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "film_genre")
    @NonNull
    private FilmGenreType filmGenreType;

    @ManyToOne
    @JoinColumn(name = "director_id")
    @NonNull
    private DirectorEntity director;

    @JoinTable(
            name = "rel_movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name="actor_id")
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ActorEntity> cast;

    public MovieEntity(String title, Integer year, String language, Integer duration, String filmGenre) {
        this.title = title;
        this.year = year;
        this.language = language;
        this.duration = duration;
        this.filmGenreType = FilmGenreType.valueOf(filmGenre);
    }

}
