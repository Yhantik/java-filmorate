# java-filmorate
Template repository for Filmorate project.
![alt_text](images/filmorate.png)

# Примеры запросов

Запрос который выведет все фильмы жанра хоррор вышедших в 2018 году<br>
SELECT f.Name AS FilmName, f.Releasdate<br>
FROM Film f<br>
JOIN Film_genre fg ON f.FilmId = fg.FilmId<br>
JOIN Genres g ON fg.GenreId = g.GenreId<br>
WHERE g.Name = 'хоррор' AND YEAR(f.Releasdate) = 2018;<br>

запрос который выведет топ-10 самых популярных фильмов жанра комедия длительностью 120 минут <br>
SELECT f.Name AS FilmName, COUNT(l.UserId) AS LikesCount<br>
FROM Film f<br>
JOIN Film_genre fg ON f.FilmId = fg.FilmId<br>
JOIN Genres g ON fg.GenreId = g.GenreId<br>
JOIN Likes l ON f.FilmId = l.FilmId<br>
WHERE g.Name = 'комедия' AND f.Duration = 120<br>
GROUP BY f.FilmId<br>
ORDER BY LikesCount DESC<br>
LIMIT 10;<br>

запрос который выведет кол-во добавленных друзей у пользователя<br>
SELECT COUNT(*) AS FriendsCount<br>
FROM Friends<br>
WHERE (UserId = <User_ID> AND IsFriend = true) OR (FriendId = <User_ID> AND IsFriend = true);<br>

запрос который отсортирует фильмы по убыванию возрастного ограничения<br>
SELECT f.Name AS FilmName, m.Name AS MpaName<br>
FROM Film f<br>
JOIN MPA m ON f.MPA = m.MPAId<br>
ORDER BY m.Name DESC;<br>

запрос который выведет любимый жанр фильмов конкретного пользователя<br>
SELECT g.Name AS GenreName, COUNT(l.UserId) AS LikesCount<br>
FROM Film_genre fg<br>
JOIN Genres g ON fg.GenreId = g.GenreId<br>
JOIN Film f ON fg.FilmId = f.FilmId<br>
JOIN Likes l ON f.FilmId = l.FilmId<br>
WHERE l.UserId = <User_ID><br>
GROUP BY g.Name<br>
ORDER BY LikesCount DESC<br>
LIMIT 1;<br>
