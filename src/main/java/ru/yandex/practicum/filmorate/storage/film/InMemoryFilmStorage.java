package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id;

    @Override
    public Film createFilm(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film deleteFilm(Film film) {
        films.remove(film.getId());

        return film;
    }

    public Film findFilmById(int filmId) {
        return films.get(filmId);
    }

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public boolean filmIsExist(int id) {
        return films.containsKey(id);
    }
}
