package ru.test.command.tgbot.demobot.repository.impl;

import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.model.users.CustomUser;
import ru.test.command.tgbot.demobot.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
public class CustomUserRepository implements Repository<CustomUser> {
    private final Map<Long, CustomUser> userMap = new HashMap<>();

    public void save(CustomUser customUser) {
        userMap.put( customUser.getId(), customUser );
    }

    @Override
    public CustomUser get(Long id) {
        return userMap.get( id );
    }

    @Override
    public Collection<CustomUser> getAll() {
        return userMap.values();
    }
}
