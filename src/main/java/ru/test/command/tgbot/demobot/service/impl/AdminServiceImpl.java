package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Override
    public boolean isAdmin(Long userTelegramId) {
        log.warn("Запросили доступ администратора: {}", userTelegramId);
        return true;
    }
}
