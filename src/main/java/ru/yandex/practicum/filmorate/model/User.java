package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private Long id;

    @Email(message = "Неверный формат электронной почты")
    private String email;

    @NotBlank(message = "Логин не должен быть пустым и содержать пробелы")
    private String login;

    private String name;

    @Past(message = "Дата рождения не должна быть в будущем")
    private LocalDate birthday;
}