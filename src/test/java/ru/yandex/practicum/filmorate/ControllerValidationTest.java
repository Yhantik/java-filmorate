package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testValidFilm() throws Exception {
        Film film = new Film(1L, "Film", "Description",
                LocalDate.of(1895, 12, 28), 120L);
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidFilmName() throws Exception {
        Film film = new Film(1L, "", "Description",
                LocalDate.of(1895, 12, 28), 120L);
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmDescription() throws Exception {
        Film film = new Film(1L, "Film", "a".repeat(201),
                LocalDate.of(1895, 12, 28), 120L);
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmReleaseDate() {
        FilmController filmController = new FilmController();
        Film film = new Film(1L, "Film", "Description",
                LocalDate.of(1895, 12, 27), 120L);
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Фильм не должен быть раньше 28.12.1895", exception.getMessage());
    }

    @Test
    public void testInvalidFilmDuration() throws Exception {
        Film film = new Film(1L, "Film", "Description",
                LocalDate.of(1895, 12, 28), 0L);
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testValidUser() throws Exception {
        User user = new User(1L, "user@example.com", "userLogin", "User",
                LocalDate.of(2002, 10, 25));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidUserEmail() throws Exception {
        User user = new User(1L, "invalid-email", "userLogin", "User",
                LocalDate.of(2002, 10, 25));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidUserLogin() throws Exception {
        User user = new User(1L, "user@example.com", " ", "User",
                LocalDate.of(2002, 10, 25));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidUserBirthday() throws Exception {
        User user = new User(1L, "user@example.com", "userLogin", "User",
                LocalDate.now().plusDays(1));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}