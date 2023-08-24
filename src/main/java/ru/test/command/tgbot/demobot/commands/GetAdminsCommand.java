/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.test.command.tgbot.demobot.commands;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.service.AdminService;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

/**
 *
 * @author User
 */
@CommandNames("/get_admins")
@Component
@Slf4j
public class GetAdminsCommand {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AdminService adminService;

    @CommandFirst
    public void perviiRazDa(CommandContext context,
            @ParamName("chatId") Long chatId,
            @ParamName("userId") Long userId) {
        String text = "";
        if (!adminService.accessStatus(userId).equals("ADMIN")) {
            text = "У вас нет полномочий для доступа к этой команде.";
        } else {
            text = "Тут должны выводиться ники и id, но из-за отсутствия БД сделать это можно только жутким го***кодом,\n"+
                    "поэтому пока так: \n";
            text += MapToString(adminService.getAllAdmins());
        }
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(text);

        context.getEngine().executeNotException(send);
    }

    @CommandOther
    public void other(CommandContext context,
            @ParamName("chatId") Long chatId) {
    }

    private String MapToString(Map<Long, String> allAdmins) {
        StringBuilder list = new StringBuilder();
        for (Map.Entry<Long, String> entry : allAdmins.entrySet()) {
            list.append(entry.getKey().toString())
                .append(" : ")
                .append(entry.getValue())
                .append("\n");
        }
        return list.toString();
    }
}
