package ru.test.command.tgbot.demobot.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.configuration.UserRolesConfig;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.repository.impl.AdminRepository;
import ru.test.command.tgbot.demobot.service.AdminService;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRolesConfig userRolesConfig;

    private Map<Long, String> temporaryUserStorage = new HashMap<>() {
        {
            put(419303542L, "ADMIN");
            put(260113861L, "ADMIN");
            put(583954324L, "ADMIN");
        }
    };

    @Override
    public boolean isAdmin(Long userTelegramId) {
        log.warn("Запросили уточнение статуса админа: {}", userTelegramId);
        return Objects.equals(accessStatus(userTelegramId), "ADMIN");
    }

    @Override
    public String accessStatus(Long userTelegramId) {
        log.warn("Запросили уточнение статуса админа: {}", userTelegramId);
        if (userRolesConfig.getUserAndStatus().get(userTelegramId) == null) {
            return "Не зарегистрирован";
        } else if (userRolesConfig.getUserAndStatus().get(userTelegramId).equals("ADMIN")) {
            return "ADMIN";
        }
        return "DEFAULT";
    }

    @Override
    public void addNewAdmin(Long newAdminId) {
        temporaryUserStorage.put(newAdminId, "ADMIN");
    }

    @Override
    public Map<Long, String> getAllAdmins() {
        return temporaryUserStorage;
    }
}
