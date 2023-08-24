package ru.test.command.tgbot.demobot.service;

import java.util.Map;

public interface AdminService {

    boolean isAdmin(Long userTelegramId);
    String accessStatus(Long userTelegramId);
    void addNewAdmin(Long newAdminId);
    Map<Long, String> getAllAdmins();
}
