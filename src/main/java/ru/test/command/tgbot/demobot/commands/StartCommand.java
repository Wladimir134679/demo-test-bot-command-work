package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

import java.util.Arrays;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.command.tgbot.demobot.service.AdminService;

@CommandNames("/start")
@Component
@Slf4j
public class StartCommand {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AdminService adminService;
    
    @CommandFirst
    public void perviiRazDa(CommandContext context,
                            @ParamName("chatId") Long chatId,
                            @ParamName("userId") Long userId,
                            Update update) {
        String userFirstName = update.getMessage().getChat().getFirstName();
        String status = "DEFAULT";
        if(adminService.isAdmin(userId)){
            status = "ADMIN";
        }
        String text = "Привет " + userFirstName + ", ваш статус: " + status + "\n\n";
        text += "\nДоступные вам команды:\n";

        text += generateListCommands();

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(text);

        context.getEngine().executeNotException(send);
    }

    @CommandOther
    public void other(CommandContext context,
                      @ParamName("chatId") Long chatId,
                      @ParamName("messageId") Long mId) {
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText("Я уже с вами поздоровался. ID вашего сообщения : " + mId + ", а данные внутри: " + context.getData());

        context.getEngine().executeNotException(send);
    }

    private String generateListCommands() {
        Map<String, Object> withAnnotation = applicationContext.getBeansWithAnnotation(CommandNames.class);
        if (withAnnotation.isEmpty()) {
            return "Нет команд";
        }
        StringBuilder list = new StringBuilder();
        for (Object value : withAnnotation.values()) {
            CommandNames annotation = value.getClass().getAnnotation(CommandNames.class);
            list.append(value.getClass().getSimpleName()).append(": ");
            for (String name : annotation.value()) {
                list.append(name).append(" | ");
            }
            list.append("\n");
        }
        return list.toString();
    }
}
