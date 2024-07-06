package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    Collection<User> getUsers();

    User addUser(User user);

    User updateUser(User user);

    void removeUser(Long id);

    Optional<User> getUserById(Long id);
}