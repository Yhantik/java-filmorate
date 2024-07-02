package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private Long id;

    @NotBlank(message = "Название не должно быть пустым")
    private String name;

    @NotNull
    @Size(max = 200, message = "Максимальная длина описания должна быть не больше 200 символов")
    private String description;

    @PastOrPresent(message = "Дата релиза не должна быть в будущем")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
}