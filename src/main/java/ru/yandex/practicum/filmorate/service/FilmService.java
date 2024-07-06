package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String message = "Фильм не должен быть раньше 28.12.1895";
            log.error(message);
            throw new ValidationException(message);
        }
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        return filmStorage.updateFilm(film);
    }

    public void removeFilm(Long id) {
        getFilmById(id);
        filmStorage.removeFilm(id);
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + id + " не найденн"));
    }

    public void addLike(Long id, Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new NotFoundException("Пользователя с id " + userId + " не существует");
        }
        if (getFilmById(id).getLikes().contains(userId)) {
            String message = "Пользователь с id " + userId + " уже лайкнул фильм";
            log.error(message);
            throw new AlreadyExistsException(message);
        }
        getFilmById(id).getLikes().add(userId);
    }

    public void removeLike(Long id, Long userId) {
        Set<Long> likes = getFilmById(id).getLikes();
        if (!likes.contains(userId)) {
            throw new NotFoundException("Лайков от пользователя с id " + userId + " не было");
        }
        likes.remove(userId);
    }

    public List<Film> getPopular(Long count) {
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(),o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}