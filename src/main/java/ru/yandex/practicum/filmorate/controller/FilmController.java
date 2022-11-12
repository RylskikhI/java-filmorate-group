package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidationService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@Slf4j
@RestController
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final InMemoryUserStorage userStorage;
    private final ValidationService validationService;
    @Autowired
    public FilmController(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                          FilmService filmService,
                          InMemoryUserStorage userStorage,
                          ValidationService validationService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
        this.userStorage = userStorage;
        this.validationService = validationService;
    }

    /**
     * Добавить фильм.
     * @param film фильм.
     * @return Film добавленный фильм.
     * @throws ValidationException если поля фильма не допустимы.
     */
    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод POST");
        if (!validationService.isValid(film)) {
            log.error("Ошибка валидации, недопустимые поля Film");
            throw new ValidationException();
        }

        return filmStorage.createFilm(film);
    }

    /**
     * Обновить информацию о фильме.
     * @param film фильм.
     * @return Film обновленный фильм.
     * @throws NotFoundException если id переданного фильма не существует.
     * @throws ValidationException если поля фильма недопустимые.
     */
    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws NotFoundException, ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод PUT");
        if (filmStorage.idNotExist(film.getId())) {
            log.error("Ошибка, фильма с таким id = " + film.getId() + " не существует.");
            throw new NotFoundException(film.getId());
        }

        if (!validationService.isValid(film)) {
            log.error("Ошибка валидации, недопустимые поля Film");
            throw new ValidationException();
        }

        return filmStorage.updateFilm(film);
    }

    /**
     * Возвращает все фильмы.
     * @return список всех пользователей.
     */
    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: /films, метод GET");

        return filmStorage.readAllFilms();
    }

    /**
     * Возвращает фильм по идентификатору.
     * @param id идентификатор фильма.
     * @return фильм.
     * @throws NotFoundException если фильма с таким идентификатором
     * не существует.
     */
    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable long id) throws NotFoundException {
        log.info("Получен запрос к эндпоинту: /films/{id}, метод GET");
        if (filmStorage.idNotExist(id)) {
            log.error("Ошибка, фильма с таким id = " + id + " не существует.");
            throw new NotFoundException(id);
        }

        return filmStorage.readFilm(id);
    }

    /**
     * Возвращает список из первых count фильмов по количеству лайков.
     * Если значение параметра count не задано, вернет первые 10.
     * @param count количество возвращаемых фильмов.
     * @return список фильмов.
     */
    @GetMapping("/films/popular")
    public List<Film> findMostPopularFilms(
            @RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Получен запрос к эндпоинту: /films/popular, метод GET");

        return filmService.findMostPopularFilms(count);
    }
}
