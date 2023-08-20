package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;

@CommandNames("/cal_his")
@Component
@Slf4j
public class Cal_his {

    private final Calculator calculator;

    Cal_his(Calculator calculator) {
        this.calculator = calculator;
    }

    @CommandFirst
    public void view(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(calculator.cal_his.toString());

        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
