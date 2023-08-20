package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@CommandNames("/cal")
@Component
@Slf4j
public class Calculator {

    List<String> memory = new ArrayList<>();
    List<String> cal_his = new ArrayList<>();

    @CommandFirst
    public void start(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText("Введите первое число");

        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @CommandOther
    public void other(CommandContext context, @ParamName("chatId") Long chatId) {
        String str = context.getUpdate().getMessage().getText();
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        switch (memory.size()) {
            case 0:
                if (isNumeric(str)) {
                    send.setText("Введите второе число");
                    memory.add(str);
                    context.getEngine().executeNotException(send);
                } else {
                    send.setText("Это не число, введите первое число");
                    context.getEngine().executeNotException(send);
                }
                break;
            case 1:
                if (isNumeric(str)) {
                    send.setText("Введите знак операции");
                    memory.add(str);
                    context.getEngine().executeNotException(send);
                } else {
                    send.setText("Это не число, введите второе число");
                    context.getEngine().executeNotException(send);
                }
                break;
            case 2:
                if ((str.charAt(0) == '-') || (str.charAt(0) == '+') || (str.charAt(0) == '*') || (str.charAt(0) == '/')) {
                    memory.add(str);
                    double one = Double.parseDouble(memory.get(0));
                    double two = Double.parseDouble(memory.get(1));
                    double result = 0;
                    switch (str) {
                        case "-":
                            result = one - two;
                            break;
                        case "+":
                            result = one + two;
                            break;
                        case "*":
                            result = one * two;
                            break;
                        case "/":
                            result = one / two;
                            break;
                    }
                    DecimalFormat format = new DecimalFormat();
                    format.setDecimalSeparatorAlwaysShown(false);

                    send.setText("Ваше результат: " + format.format(result));
                    context.getEngine().executeNotException(send);

                    cal_his.add(memory.get(0) + memory.get(2) + memory.get(1) + " = " + format.format(result));
                    System.out.println(cal_his);
                    memory.clear();
                    result = 0;
                } else {
                    send.setText("Этот знак не является математическим символом, \nвведите верный знак");
                    context.getEngine().executeNotException(send);
                }
                break;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

