package com.cleverpy.data.entities;

public enum FilmGenreType {

    ACTION,
    COMEDY,
    CRIME,
    FANTASY,
    HORROR,
    THRILLER;

    public static boolean existsFilmGenre(String filmGenre) {
        for (FilmGenreType filmGenreType : FilmGenreType.values()) {
            if (filmGenreType.name().equalsIgnoreCase(filmGenre)) {
                return true;
            }
        }
        return false;
    }

}
