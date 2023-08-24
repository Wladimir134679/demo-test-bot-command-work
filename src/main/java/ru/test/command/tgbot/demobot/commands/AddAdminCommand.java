/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.test.command.tgbot.demobot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.command.tgbot.demobot.service.AdminService;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

/**
 *
 * @author User@Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AdminService adminService;
 */
@CommandNames("/add_admin")
@Component
@Slf4j
public class AddAdminCommand {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AdminService adminService;

    @CommandFirst
    public void perviiRazDa(CommandContext context,
            @ParamName("chatId") Long chatId) {
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText("Введите ID пользователя, которого хотите сделать администратором: ");

        context.getEngine().executeNotException(send);
    }

    @CommandOther
    public void other(CommandContext context,
            @ParamName("chatId") Long chatId,
            @ParamName("userId") Long userId,
            Update update) {
        String text = "";
        if (!adminService.accessStatus(userId).equals("ADMIN")) {
            text = "У вас нет полномочий добавлять админов.";
        } else {
            Long newAdminId = Long.valueOf(update.getMessage().getText());
            adminService.addNewAdmin(newAdminId);
            text = "Готово!";
        }

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(text);

        context.getEngine().executeNotException(send);
    }
}
