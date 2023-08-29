package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.service.impl.AdminServiceImpl;
import ru.test.command.tgbot.demobot.service.impl.CalculatorServiceImpl;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;

@CommandNames("/cal_his")
@Component
@Slf4j
public class CalculatorHistoryList {

    @Autowired
    private final CalculatorServiceImpl service;
    private final AdminServiceImpl adminService;

    public CalculatorHistoryList(CalculatorServiceImpl service, AdminServiceImpl adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @CommandFirst
    public void view(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        if(adminService.isAdmin(chatId)) send.setText(service.listAll().toString());

        else send.setText(service.listOut(chatId).toString());

        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
