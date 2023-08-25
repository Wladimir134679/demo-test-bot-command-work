package ru.test.command.tgbot.demobot.repository.impl;


import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminRepository implements Repository<Admin> {
    private final Map<Long, Admin> userMap = new HashMap<>();



    public void save(Admin admin) {
        userMap.put( admin.getId(), admin );
    }

    @Override
    public Admin get(Long id) {
        return userMap.get( id );
    }

    @Override
    public Collection<Admin> getAll() {
        return userMap.values();
    }

}
