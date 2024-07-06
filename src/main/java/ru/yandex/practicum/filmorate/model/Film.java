package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;
    private final Set<Long> likes = new HashSet<>();

    @NotBlank(message = "Название не должно быть пустым")
    private String name;

    @NotNull
    @Size(max = 200, message = "Максимальная длина описания должна быть не больше 200 символов")
    private String description;

    @PastOrPresent(message = "Дата релиза не должна быть в будущем")
    @MinDate(value = "1895-12-28", message = "Фильм не должен быть раньше 28.12.1895")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
}