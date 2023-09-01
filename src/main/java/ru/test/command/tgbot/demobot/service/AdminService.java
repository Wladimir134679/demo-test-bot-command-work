package ru.test.command.tgbot.demobot.service;

import java.util.List;
import java.util.Map;

public interface AdminService {

    boolean isAdmin(Long userTelegramId);

    void addNewAdmin(Long newAdminId);

    List<Long> getAllAdmins();
}
