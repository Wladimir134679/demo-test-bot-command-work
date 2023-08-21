package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.repository.impl.AdminRepository;
import ru.test.command.tgbot.demobot.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService, MyAdminMethods {

    private final AdminRepository repository;

    public AdminServiceImpl(AdminRepository repository) {
        this.repository = repository;
    }

    {
        Admin newAdmin = new Admin();
        newAdmin.setId(6488103958L);
    }

    @Override
    public boolean isAdmin(Long userTelegramId) {
        log.warn( "Запросили доступ администратора: {}", userTelegramId );
        return repository.get( userTelegramId ) != null;
    }

    @Override
    public void add(Admin newAdmin) {
        repository.save( newAdmin );
    }
}
