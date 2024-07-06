package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;
    private final Set<Long> friends = new HashSet<>();

    @Email(message = "Неверный формат электронной почты")
    private String email;

    @NotBlank(message = "Логин не должен быть пустым и содержать пробелы")
    private String login;

    private String name;

    @Past(message = "Дата рождения не должна быть в будущем")
    private LocalDate birthday;
}