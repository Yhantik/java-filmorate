package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        getUserById(user.getId());
        return userStorage.updateUser(user);
    }

    public void removeUser(Long id) {
        getUserById(id);
        userStorage.removeUser(id);
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
    }

    public void addFriend(Long id, Long friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    public void removeFriend(Long id, Long friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    public List<User> getAllFriends(Long id) {
        User user = getUserById(id);
        List<User> friendsList = new ArrayList<>();
        Set<Long> friendsIds = user.getFriends();
        if (friendsIds != null) {
            for (Long friend : friendsIds) {
                friendsList.add(getUserById(friend));
            }
        }
        return friendsList;
    }

    public List<User> getMutualFriends(Long firstUser, Long secondUser) {
        List<User> firstUserFriends = getAllFriends(firstUser);
        List<User> secondUserFriends = getAllFriends(secondUser);
        firstUserFriends.retainAll(secondUserFriends);
        return firstUserFriends;
    }
}