package ru.test.command.tgbot.demobot.commands;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.users.Admin;
import ru.test.command.tgbot.demobot.service.impl.AdminServiceImpl;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;


@Slf4j
@Component
@CommandNames("/addAdmin")
public class AddAdmin {

    private final AdminServiceImpl adminService;

    public AddAdmin(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @CommandFirst
    public void perviiRazDa(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) throws TelegramApiException {
        Long userId = engine.getMe().getId();
        Admin newAdmin = new Admin();
        newAdmin.setId( userId );
        adminService.add( newAdmin );
        var send = new SendMessage();
        send.setChatId( String.valueOf( chatId ) );
        send.setText( " Пользователь с id :" + chatId + " -  добавлен в администраторы " );
        try{
            engine.execute( send );
        } catch(TelegramApiException e){
            throw new RuntimeException( e );
        }
    }

    @CommandOther
    public void other(CommandContext context, @ParamName("chatId") Long chatId, @ParamName("messageId") Long mId) {
        var send = new SendMessage();
        send.setChatId( String.valueOf( chatId ) );
        send.setText( " Пользователь с id :" + chatId + " -   уже добавлен в администраторы \n" +
                "введите другую команду" );
        context.getEngine().executeNotException( send );
    }
}
