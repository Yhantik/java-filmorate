package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Получение всех пользователей");
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Пользователь создан: {}", user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Пользователь обновлен: {}", user);
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        log.info("Получен пользователь {}", userService.getUserById(id));
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") Long id) {
        log.info("Удален пользователь{}", userService.getUserById(id));
        userService.removeUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long id,
                          @PathVariable("friendId") Long friendId) {
        log.info("Пользователю с id {} добавлен друг с id {}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") Long id,
                             @PathVariable("friendId") Long friendId) {
        log.info("У пользователя с id {} удален друг с id {}", id, friendId);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable("id") Long id) {
        log.info("Получение списка друзей у пользователя с id {}", id);
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable("id") Long id,
                                        @PathVariable("otherId") Long otherId) {
        log.info("Получение списка общих друзей пользователя с id {} и пользователя с id {}", id, otherId);
        return userService.getMutualFriends(id, otherId);
    }
}