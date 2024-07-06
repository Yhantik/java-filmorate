package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerValidationTest {

    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController(filmService);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testValidFilm() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(120L)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidFilmName() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .name("")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(120L)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmDescription() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .name("Film")
                .description("a".repeat(201))
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(120L)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmReleaseDate() {
        Film film = Film.builder()
                .id(1L)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(120L)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Фильм не должен быть раньше 28.12.1895", exception.getMessage());
    }

    @Test
    public void testInvalidFilmDuration() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(0L)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testValidUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .login("userLogin")
                .name("User")
                .birthday(LocalDate.of(2002, 10, 25))
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidUserEmail() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("invalid-email")
                .login("userLogin")
                .name("User")
                .birthday(LocalDate.of(2002, 10, 25))
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidUserLogin() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .login(" ")
                .name("User")
                .birthday(LocalDate.of(2002, 10, 25))
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidUserBirthday() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .login("userLogin")
                .name("User")
                .birthday(LocalDate.now().plusDays(1))
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}