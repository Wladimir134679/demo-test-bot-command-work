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
import ru.wdeath.managerbot.lib.bot.command.ManagerCommandContainerService;

import java.util.ArrayList;
import java.util.Map;

@CommandNames("/start")
@Component
@Slf4j
public class TestCommand {


    @Autowired
    private ApplicationContext applicationContext;

    @CommandFirst
    public void perviiRazDa(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {
        String text = "И тебе привет\n\n";

        text += generateListCommands();

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(text);

        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @CommandOther
    public void other(CommandContext context, @ParamName("chatId") Long chatId, @ParamName("messageId") Long mId) {
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText("Я уже с тобой поздоровался! Твой сообщение id: " + mId + ", а данные внутри: " + context.getData());

        context.getEngine().executeNotException(send);
    }

    private String generateListCommands() {
        Map<String, Object> withAnnotation = applicationContext.getBeansWithAnnotation(CommandNames.class);
        if(withAnnotation.isEmpty())
            return "Нет команд";
        StringBuilder list = new StringBuilder();
        for (String name : withAnnotation.keySet()) {
            list.append(name).append("\n");
        }
        return list.toString();
    }
}
