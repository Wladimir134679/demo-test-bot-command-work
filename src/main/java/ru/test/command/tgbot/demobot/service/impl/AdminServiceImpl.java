package ru.test.command.tgbot.demobot.service.impl;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.configuration.UserRolesConfig;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.repository.impl.AdminRepository;
import ru.test.command.tgbot.demobot.service.AdminService;
import ru.test.command.tgbot.demobot.service.MyAdminMethods;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService, MyAdminMethods {

    private final AdminRepository repository;

    public AdminServiceImpl(AdminRepository repository) {
        this.repository = repository;
    }

    {
        Admin newAdmin = new Admin();
        newAdmin.setId(6488103958L);
    }

    @Autowired
    private UserRolesConfig userRolesConfig;

    //эта мапа симулирует работу БД, поскольку настоящей БД нет пока 
    private Map<Long, String> temporaryUserStorage = new HashMap<>() {
        //при проверке на права доступа значения этой мапы не используются! 
        //они тут закостылены только для вывода в /get_admins
        //при любых изменениях статуса в application.yaml нужно тут вручную убирать и добавлять админов
        {
            put(419303542l, "ADMIN");    
            put(260113861l, "ADMIN");
        }
    };

    @Override
    public boolean isAdmin(Long userTelegramId) {
        log.warn("Запросили уточнение статуса админа: {}", userTelegramId);
        return false;
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
