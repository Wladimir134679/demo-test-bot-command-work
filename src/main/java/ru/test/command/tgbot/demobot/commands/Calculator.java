package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.users.CalculateDataUser;
import ru.test.command.tgbot.demobot.service.impl.CalculatorServiceImpl;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

import java.text.DecimalFormat;

@CommandNames("/cal")
@Component
@Slf4j
public class Calculator {

    @Autowired
    private final CalculatorServiceImpl service;

    public Calculator(CalculatorServiceImpl service) {
        this.service = service;
    }


    @CommandFirst
    public void start(CommandContext context, TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {

        context.getUserBotSession().setData(new CalculateDataUser(chatId));

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
    public void other(CommandContext context, @ParamName("chatId") Long chatId, Message message) {

        var send = new SendMessage();
        String str = null;

        send.setChatId(String.valueOf(chatId));

        CalculateDataUser data = (CalculateDataUser) context.getUserBotSession().getData();

        if (message.getChatId() == data.getUserId())
            str = message.getText();

        if (str != null) {
            switch (data.getStep()) {
                case 0:
                    if (isNumeric(str)) {
                        send.setText("Введите второе число");
                        data.setFirstNumber(Double.parseDouble(str));
                        data.setStep(1);
                        context.getEngine().executeNotException(send);
                    } else {
                        send.setText("Это не число, введите первое число");
                        context.getEngine().executeNotException(send);
                    }
                    break;
                case 1:
                    if (isNumeric(str)) {
                        send.setText("Введите знак операции");
                        data.setSecondNumber(Double.parseDouble(str));
                        data.setStep(2);
                        context.getEngine().executeNotException(send);
                    } else {
                        send.setText("Это не число, введите второе число");
                        context.getEngine().executeNotException(send);
                    }
                    break;
                case 2:
                    if ((str.charAt(0) == '-') || (str.charAt(0) == '+') || (str.charAt(0) == '*') || (str.charAt(0) == '/')) {
                        data.setSign(str.charAt(0));

                        switch (str) {
                            case "-":
                                data.setResult(data.getFirstNumber() - data.getSecondNumber());
                                break;
                            case "+":
                                data.setResult(data.getFirstNumber() + data.getSecondNumber());
                                break;
                            case "*":
                                data.setResult(data.getFirstNumber() * data.getSecondNumber());
                                break;
                            case "/":
                                data.setResult(data.getFirstNumber() / data.getSecondNumber());
                                break;
                        }
                        DecimalFormat format = new DecimalFormat();
                        format.setDecimalSeparatorAlwaysShown(false);

                        send.setText("Ваше результат: " + format.format(data.getResult()) + "\nДля нового расчета введите /cal");
                        context.getEngine().executeNotException(send);
                        data.setStep(3);
                        service.listIn(data.getUserId(), data);

                    } else {
                        send.setText("Этот знак не является математическим символом, \nвведите верный знак");
                        context.getEngine().executeNotException(send);
                    }
                    break;
                case 3:
                    send.setText("\nДля нового расчета введите /cal");
                    context.getEngine().executeNotException(send);
                    break;
            }
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
