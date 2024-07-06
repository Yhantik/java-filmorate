package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Получение всех фильмов");
        return filmService.getFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Фильм добавлен: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Фильм обновлен: {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Long id) {
        log.info("Получен фильм{}", filmService.getFilmById(id));
        return filmService.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void removeFilm(@PathVariable("id") Long id) {
        log.info("Удален фильм{}", getFilmById(id));
        filmService.removeFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Long id,
                        @PathVariable("userId") Long userId) {
        log.info("Добавлен лайк фильму с id {} пользователем с id {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Long id,
                           @PathVariable("userId") Long userId) {
        log.info("Удален лайк у фильма с id {} пользователем с id {}", id, userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10")
                                     @Positive(message = "count должен быть положительным") Long count) {
        log.info("Получены популярные фильмы");
        return filmService.getPopular(count);
    }
}