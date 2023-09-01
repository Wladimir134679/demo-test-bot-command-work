package ru.test.command.tgbot.demobot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.configuration.AdminConfig;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.repository.impl.AdminRepository;
import ru.test.command.tgbot.demobot.service.AdminService;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminConfig adminConfig;
    
    private List<Long> temporaryAdminStorage = new ArrayList<>() {
        {
            add(419303542L);
            add(260113861L);
        }
    };

    @Override
    public boolean isAdmin(Long userTelegramId) {
        log.warn("Запросили уточнение статуса админа: {}", userTelegramId);
        if(adminConfig.getAdmins().contains(userTelegramId)){
            return true;
        }
        return false;
    }

    @Override
    public void addNewAdmin(Long newAdminId) {
        temporaryAdminStorage.add(newAdminId);
    }

    @Override
    public List<Long> getAllAdmins() {
        return temporaryAdminStorage;
    }
   
}
