package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    private int id;

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод POST");
        if (!ValidationService.valid(user)) {
            log.debug("Ошибка");
            throw new ValidationException();
        }
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод PUT");
        if (!users.containsKey(user.getId()) || !ValidationService.valid(user)) {
            log.debug("Ошибка");
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Получен запрос к эндпоинту: /users, метод GET");
        return new ArrayList<>(users.values());
    }
}